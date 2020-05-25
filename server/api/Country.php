<?php
namespace api;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);
require_once(dirname(__FILE__,2) . '/model/Country.php');
require_once(dirname(__FILE__,2) . '/database/Country.php');
require_once(dirname(__FILE__,2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__,1) . '/Api.php');
class Country extends Api{

  private $model;

  function __construct(){
      parent::__construct();
      set_error_handler(array($this, 'error_handler'));
  }

  function insert() : void {
      try{
          $this->model = new \model\Country($_GET['countrycode'], $_GET['name'], $_GET['description']);

          $countrystatement = new \database\Country();
          $code = $countrystatement->insert($this->model);
          $code = substr($code, 0, 2);

          parent::setHttpCode($code);

      }catch(\PDOException $e){
          parent::setHttpCode($e->getCode());
      }catch(\exception\NullPointerException $e){
          header('HTTP/1.0 400 Bad Request');
          \var_dump($e);
          restore_error_handler();
      }
  }

  public function select(){
  try{
    $this->model = new \model\Country();
    $queryBuilder = parent::buildQuery($this->model);

    if(null != $_GET['where']){
      $arguments = parent::rebuildArguments($_GET['where']);
      $approvedArguments = $this->model->getVariables();
      foreach($arguments as $value){
          if($value[0] == 'description'){
              $this->model->setDescription($value[2]);
          }else if($value[0] == 'name'){
              $this->model->setName($value[2]);
          }elseif($value[0] == 'countrycode'){
            $this->model->setCountrycode($value[2]);
          }
    }
  }

  $countrystatement = new \database\Country($queryBuilder);
  $codeAndResult = $countrystatement->select($this->model);

  if($codeAndResult[0][1] == '00'){
      header('Content-Type: application/json');
      echo json_encode($codeAndResult[1][0]);
  }

$code = substr($code, 0, 2);
parent::setHttpCode($code);
}catch(\PDOException $e){
    parent::setHttpCode($e->getCode());
}catch(\exception\NullPointerException $e){
  header('HTTP/1.0 400 Bad Request');
  \var_dump($e);
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
}

$country = new Country();
if(isset($_GET['countrycode'])){
  $country->insert();
}else{
  $country->select();
}
?>
