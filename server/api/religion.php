<?php
namespace api;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);
require_once(dirname(__FILE__,2) . '/model/Religion.php');
require_once(dirname(__FILE__,2) . '/database/Religion.php');
require_once(dirname(__FILE__,2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__,1) . '/CRInterface.php');
require_once(dirname(__FILE__,1) . '/Api.php');

class Religion extends Api implements CRInterface{

    private $model;

    function __construct(){
        parent::__construct();
        set_error_handler(array($this, 'error_handler'));
    }

    function insert() {
        try{
            $this->model = new \model\Religion($_GET['name']);
            //create a new database statement and execute it
            $religieStatement = new \database\Religion();
            $code = $religieStatement->insert($this->model);
            //reduce the error code to the error class so it can be used by the http code class
            $code = substr($code, 0, 2);
            //return the right http code, then die
            parent::setHttpCode($code);

        }catch(\PDOException $e){
            //return the right http code, then die
            parent::setHttpCode($e->getCode());
        }catch(\exception\NullPointerException $e){
            header('HTTP/1.0 400 Bad Request');
            //return the error message so the code can be more easily debuged
            echo $e->getMessage();
            restore_error_handler();
        }
    }

    public function select() {
      try{
          //extract all data from the get parameters so it can be used
          $this->model = new \model\Religion();
          $queryBuilder = parent::buildQuery($this->model);

          //I don't know how to get the decoded arguments to the database, so I will call rebuildArguments again
          if(null != $_GET['where']){
              $arguments = parent::rebuildArguments($_GET['where']);
              $approvedArguments = $this->model->getVariables();
              foreach($arguments as $value){
                  if($value[0] == 'id'){
                      $this->model->setId($value[2]);
                  }else if($value[0] == 'name'){
                      $this->model->setName($value[2]);
                  }
              }
          }

          $religionStatement = new \database\Religion($queryBuilder);
          $codeAndResult = $religionStatement->select($this->model);

          if($codeAndResult[0][1] == '00'){
              header('Content-Type: application/json');
              echo json_encode($codeAndResult[1][0]);
          }

          $code = substr($codeAndResult[0][1], 0, 2);

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

$religion = new Religion();
if(isset($_GET['name'])){
    $religion->insert();
}else{
    //$religion->select();
}
?>
