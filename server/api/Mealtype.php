<?php
namespace api;
require_once('../model/Mealtype.php');
require_once('../database/Mealtype.php');
require_once('../exception/NullPointerException.php');
require_once('Api.php');
class Mealtype extends Api{

  private $model;

  function __construct(){
      parent::__construct();

      set_error_handler(array($this, 'error_handler'));
  }

  function insert() {
      try{
          $this->model = new \model\Mealtype();
          $this->model->setName($_GET['name']);

          $mealtypestatement = new \database\Mealtype();
          $code = $mealtypestatement->insert($this->model);
          echo $code;
          $code = substr($code, 0, 2);

          parent::setHttpCode($code);
      }catch(\PDOException $e){
          echo $e->getCode();
          error_log($e->getCode());
          parent::setHttpCode($e->getCode());
      }catch(\exception\NullPointerException $e){
          echo 'NullPointerException';
          header('HTTP/1.0 400 Bad Request');
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

}

$mealtype = new Mealtype();
$mealtype->insert();

 ?>
