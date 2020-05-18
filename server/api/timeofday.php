<?php
namespace api;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(-1);
require_once(dirname(__FILE__, 2) . '/model/TimeOfDay.php');
require_once(dirname(__FILE__, 1) . '/CRInterface.php');
require_once(dirname(__FILE__, 2) . '/database/TimeOfDay.php');
require_once(dirname(__FILE__, 2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__, 1) . '/Api.php');

class TimeOfDay extends Api implements CRInterface{

    private $model;

    function __construct(){
        parent::__construct();

        set_error_handler(array($this, 'error_handler'));
    }

    function insert() : void{
        try{
            $this->model = new \model\TimeOfDay($_GET['name']);
            $this->model->setName($_GET['name']);

            $tijdvakStatement = new \database\TimeOfDay();
            $code = $tijdvakStatement->insert($this->model);
            $code = substr($code, 0, 2);

            parent::setHttpCode($code);
        }catch(\PDOException $e){
            parent::setHttpCode($e->getCode());
        }catch(\exception\NullPointerException $e){
            header('HTTP/1.0 400 Bad Request');
            restore_error_handler();
        }
    }

    public function select() : void{
      try{
          //extract all data from the get parameters so it can be used
          $this->model = new \model\TimeOfDay();
          $queryBuilder = parent::buildQuery($this->model);

          //I don't know how to get the decoded arguments to the database, so I will call rebuildArguments again
          if(null != $_GET['where']){
              $arguments = parent::rebuildArguments($_GET['where']);
              $approvedArguments = $this->model->getVariables();
              foreach($arguments as $value){
                  if($value[0] == 'name'){
                      $this->model->setName($value[2]);
                  }
              }
          }

          $codeAndResult = (new \database\TimeOfDay($queryBuilder))->select($this->model);

          $code = substr($codeAndResult[0][1], 0, 2);

          if($code === '00'){
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

    function error_handler($errno, $errstr, $errfile, $errline){
        if($errstr == 'Undefined index: name'){
            throw new \exception\NullPointerException("Name value isn't passed");
        }else{
            restore_error_handler();
        }
    }
}

$timeOfDay = new TimeOfDay();
if(isset($_GET['name'])){
    $timeOfDay->insert();
}else{
    //$timeOfDay->select();
}

 ?>
