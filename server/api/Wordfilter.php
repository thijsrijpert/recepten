<?php
namespace api;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);
require_once(dirname(__FILE__,2) . '/model/Wordfilter.php');
require_once(dirname(__FILE__, 1) . '/CRUInterface.php');
require_once(dirname(__FILE__,2) . '/database/Wordfilter.php');
require_once(dirname(__FILE__,2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__,1) . '/Api.php');
  class Wordfilter extends Api{

    private $model;

    function __construct(){
        parent::__construct();
        set_error_handler(array($this, 'error_handler'));
    }

    function insert(){
        try{
            $this->model = new \model\Wordfilter($_GET['word']);
            $wordfilterStatement = new \database\Wordfilter();
            $code = $wordfilterStatement->insert($this->model);
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

        $codeAndResult = (new \database\Wordfilter($queryBuilder))->select($this->model);
        $code = substr($codeAndResult[0], 0, 2);

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

    function update(){
      try{
          $modelNew = new \model\Wordfilter();
          $modelOld = $this->getWhereModel();

          $queryBuilderSelect = parent::buildQuery($modelOld);
          $queryBuilderUpdate = parent::buildUpdate($modelNew);


          if(null != $_GET['set']){
              $arguments = parent::rebuildArguments($_GET['set']);
              $approvedArguments = $this->model->getVariables();
              foreach($arguments as $value){
                  if($value[0] == 'word'){
                      $this->model->setWord($value[2]);
                  }
              }
          }

          $statement = new \database\Wordfilter($queryBuilderSelect, $queryBuilderUpdate);
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
        if($errstr == 'Undefined index: word'){
            throw new \exception\NullPointerException("Get value isn't passed");
        }else{
            restore_error_handler();
        }
    }

    function getWhereModel() : \model\Model {

        $model = new \model\Wordfilter();

        if(null != $_GET['where']){
            $arguments = parent::rebuildArguments($_GET['where']);
            $approvedArguments = $this->model->getVariables();
            foreach($arguments as $value){
                if($value[0] == 'word'){
                    $this->model->setWord($value[2]);
                }
            }
        }

        return $model;
    }
  }

    $wordfilter = new Wordfilter();
    if(isset($_GET['word'])){
      $wordfilter->insert();
    }else if(isset($_GET['set'])){
        $wordfilter->update();
    }else{
      $wordfilter->select();
    }
 ?>
