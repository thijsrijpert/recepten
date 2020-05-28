<?php
namespace api;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);
require_once(dirname(__FILE__,2) . '/model/Religion.php');
require_once(dirname(__FILE__,2) . '/database/Religion.php');
require_once(dirname(__FILE__,2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__,1) . '/CRUInterface.php');
require_once(dirname(__FILE__,1) . '/Api.php');

class Religion extends Api implements CRUInterface{

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
          $this->model = $this->getWhereModel();
          $queryBuilder = parent::buildQuery($this->model);

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

    function update(){
      try{
          $modelOld = $this->getWhereModel();
          $modelNew = $this->getSetModel();

          $queryBuilderSelect = parent::buildQuery($modelOld);
          $queryBuilderUpdate = parent::buildUpdate($modelNew);

          $statement = new \database\Religion($queryBuilderSelect, $queryBuilderUpdate);

          $result = $statement->select($modelOld);

          if(count($result[1][0]) == 1){
              $resultUpdate = $statement->update($modelNew, $modelOld);
              parent::setHttpCode($resultUpdate);
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

    function getWhereModel() : \model\Model {
        $model =  new \model\Religion();

        //I don't know how to get the decoded arguments to the database, so I will call rebuildArguments again
        if(null != $_GET['where']){
            $arguments = parent::rebuildArguments($_GET['where']);
            $approvedArguments = $model->getVariables();
            $model = $this->getModel($model, $arguments, $approvedArguments);
        }

        return $model;
    }

    function getSetModel() : \model\Model {
      $model =  new \model\Religion();

      //I don't know how to get the decoded arguments to the database, so I will call rebuildArguments again
      if(null != $_GET['set']){
          $arguments = parent::rebuildArguments($_GET['set']);
          $approvedArguments = $model->getUpdateVariables();
          $model = $this->getModel($model, $arguments, $approvedArguments);
      }

      return $model;
    }

    function getModel(\model\Model $model, array $arguments, array $approvedArguments) : \model\Model {
      foreach($arguments as $value){
          if($value[0] == 'id'){
              $model->setId(end($value));
          }else if($value[0] == 'name'){
              $model->setName(end($value));
          }
      }

      return $model;
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
}else if(isset($_GET['set'])){
    $religion->update();
}else{
    $religion->select();
}
?>
