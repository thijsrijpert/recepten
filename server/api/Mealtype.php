<?php
namespace api;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);
require_once(dirname(__FILE__,2) . '/model/Mealtype.php');
require_once(dirname(__FILE__, 1) . '/CRUInterface.php');
require_once(dirname(__FILE__,2) . '/database/Mealtype.php');
require_once(dirname(__FILE__,2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__,1) . '/Api.php');
class Mealtype extends Api implements CRUInterface{

  private $model;

  function __construct(){
      parent::__construct();
      set_error_handler(array($this, 'error_handler'));
  }

  function insert() {
      try{
          $this->model = new \model\Mealtype($_GET['name']);
          $mealtypestatement = new \database\Mealtype();
          $code = $mealtypestatement->insert($this->model);
          $code = substr($code, 0, 2);

          parent::setHttpCode($code);

      }catch(\PDOException $e){
          parent::setHttpCode($e->getCode());
      }catch(\exception\NullPointerException $e){
          header('HTTP/1.0 400 Bad Request');
          restore_error_handler();
      }
  }

  public function select(){
    try{
        $this->model = $this->getWhereModel();
        $queryBuilder = parent::buildQuery($this->model);

        $codeAndResult = (new \database\Mealtype($queryBuilder))->select($this->model);
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
      //set the datatype to json for consistancy with all select query's
      header('Content-Type: application/json');
      //return the error code for easy debug
      echo json_encode($e->getMessage());
      restore_error_handler();
    }
  }


  public function update(){
    try{
        $modelNew = new \model\Mealtype();
        $modelOld = $this->getWhereModel();

        $queryBuilderSelect = parent::buildQuery($modelOld);
        $queryBuilderUpdate = parent::buildUpdate($modelNew);

        if(null != $_GET['set']){
            $arguments = parent::rebuildArguments($_GET['set']);
            $approvedArguments = $modelNew->getVariables();
            foreach($arguments as $value){
                if($value[0] == 'name'){
                    $modelNew->setName($value[1]);
                }
            }
        }

        $statement = new \database\Mealtype($queryBuilderSelect, $queryBuilderUpdate);
        $result = $statement->select($modelOld);

        if(count($result[1][0]) === 1){
            $code = substr($statement->update($modelNew, $modelOld), 0, 2);
            parent::setHttpCode($code);
        }else{
            throw new \exception\NullPointerException("The request changed more than one record, please change your where scope");
        }
      }catch(\PDOException $e){
          parent::setHttpCode($e->getCode());
      }catch(\exception\NullPointerException $e){
          header('HTTP/1.0 400 Bad Request');
          //set the datatype to json for consistancy with all select query's
          header('Content-Type: application/json');
          //return the error code for easy debug
          echo json_encode($e->getMessage());
          restore_error_handler();
      }
  }


  function error_handler($errno, $errstr, $errfile, $errline){
      if($errstr == 'Undefined index: name'){
          throw new \exception\NullPointerException("Get value isn't passed");
      }else{
          restore_error_handler();
      }
  }

  function getWhereModel() : \model\Model {

      $model = new \model\Mealtype();

      //I don't know how to get the decoded arguments to the database, so I will call rebuildArguments again
      if(null != $_GET['where']){
          $arguments = parent::rebuildArguments($_GET['where']);
          $approvedArguments = $model->getVariables();
          foreach($arguments as $value){
              if($value[0] == 'name'){
                  $model->setName($value[2]);
              }
          }
      }
      return $model;
  }
}

$mealtype = new Mealtype();
if(isset($_GET['name'])){
  $mealtype->insert();
}elseif(isset($_GET['set'])) {
  $mealtype->update();
}else{
  $mealtype->select();
}

 ?>
