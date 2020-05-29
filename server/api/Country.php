<?php
namespace api;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);
require_once(dirname(__FILE__,2) . '/model/Country.php');
require_once(dirname(__FILE__, 1) . '/CRUInterface.php');
require_once(dirname(__FILE__,2) . '/database/Country.php');
require_once(dirname(__FILE__,2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__,1) . '/Api.php');
class Country extends Api implements CRUInterface{

  private $model;

  function __construct(){
      parent::__construct();
      set_error_handler(array($this, 'error_handler'));
  }

  function insert() {
      try{
          $this->model = new \model\Country($_GET['countrycode'], $_GET['name'], $_GET['description']);
          $countrystatement = new \database\Country();
          $code = $countrystatement->insert($this->model);
          $code = substr($code, 0, 2);

          parent::setHttpCode($code);
        }catch(\PDOException $e){
            parent::setHttpCode($e->getCode());
        }catch(exception\NullPointerException $e){
          header('HTTP/1.0 400 Bad Request');
          echo $e->getMessage();
          restore_error_handler();
        }
    }

  public function select(){
  try{
    $this->model = new \model\Country();
    $queryBuilder = parent::buildQuery($this->model);

  $codeAndResult = (new \database\Country($queryBuilder))->select($this->model);
  $code = substr($codeAndResult[0],0,2);

  if($codeAndResult[0] == '00'){
      header('Content-Type: application/json');
      echo json_encode($codeAndResult[1][0]);
  }

      parent::setHttpCode($code);
    }catch(\PDOException $e){
      parent::setHttpCode($e->getCode());
    }catch(\exception\NullPointerException $e){
      header('HTTP/1.0 400 Bad Request');
      restore_error_handler();
    }
}

  public function update (){
    try{
      $modelNew = new \model\Model\Country();
      $modelOld = $this->getWhereModel();

      $queryBuilderSelect = parent::buildQuery($modelOld);
      $queryBuilderUpdate = parent::buildUpdate($modelNew);

      if(null != $_GET['set']){
        $arguments = parent::rebuildArguments($_GET['set']);
        $approvedArguments = $this->model->getVariables();
        foreach($arguments as $value){
            if($value[0] == 'countrycode'){
                $this->model->setCountrycode($value[1]);
            }else if($value[0] == 'name'){
                $this->model->setName($value[1]);
            }elseif($value[0] == 'description'){
              $this->model->setDescription($value[1]);
            }
      }
    }

    $statement = new \database\Country($queryBuilderSelect, $queryBuilderUpdate);
    $result = $statement->select($this->$modelOld);

    if(count($result[1][0]) === 1){
        $code = substr($statement->update($modelNew, $modelOld), 0, 2);
        parent::setHttpCode($code);
    }else{
        throw new \exception\NullPointerException("The request changed more than one record, please change your where scope");
      }
    }catch(\PDOException $e){
      header('HTTP/1.0 400 Bad Request');
      //set the datatype to json for consistancy with all select query's
      header('Content-Type: application/json');
      //return the error code for easy debug
      echo json_encode($e->getMessage());
      restore_error_handler();
    }
  }

function error_handler($errno, $errstr, $errfile, $errline){
    if($errstr == 'Undefined index: countrycode' || $errstr == 'Undefined index: name' || $errstr == 'Undefined index: description'){
        throw new \exception\NullPointerException("Get value isn't passed");
    }else{
        restore_error_handler();
    }
}

function getWhereModel() : \model\Model {

    $model = new \model\Country();

    if(null != $_GET['where']){
      $arguments = parent::rebuildArguments($_GET['where']);
      $approvedArguments = $this->model->getVariables();
      foreach($arguments as $value){
          if($value[0] == 'countrycode'){
              $this->model->setCountrycode($value[2]);
          }else if($value[0] == 'name'){
              $this->model->setName($value[2]);
          }elseif($value[0] == 'description'){
            $this->model->setDescription($value[2]);
          }
    }
  }
    return $model;
}

}

$country = new Country();
if(isset($_GET['countrycode'])){
  $country->insert();
}else if(isset($_GET['set'])){
  $country->update();
}else{
  $country->select();
}

?>
