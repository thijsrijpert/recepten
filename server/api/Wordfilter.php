<?php
namespace api;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);
require_once(dirname(__FILE__,2) . '/model/Wordfilter.php');
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
          $this->model = new \model\Wordfilter();
          $queryBuilder = parent::buildQuery($this->model);

          if(null != $_GET['where']){
              $arguments = parent::rebuildArguments($_GET['where']);
              $approvedArguments = $this->model->getVariables();
              foreach($arguments as $value){
                  if($value[0] == 'word'){
                      $this->model->setWord($value[2]);
                  }
              }
          }

          $wordfilterStatement = new \database\Wordfilter($queryBuilder);

          $codeAndResult = $wordfilterStatement->select($this->model);

          if($codeAndResult[0] == '00'){
              header('Content-Type: application/json');
              echo json_encode($codeAndResult[1][0]);
          }

          $code = substr($codeAndResult[0],0,2);

          parent::setHttpCode($code);
      }catch(\PDOException $e){
          parent::setHttpCode($e->getCode());
      }catch(\exception\NullPointerException $e){
          header('HTTP/1.0 400 Bad Request');
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
  }

    $wordfilter = new Wordfilter();
    if(isset($_GET['word'])){
      $wordfilter->insert();
    }else{
      $wordfilter->select();
    }
 ?>
