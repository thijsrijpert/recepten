<?php
namespace api;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(-1);
require_once(dirname(__FILE__, 2) . '/model/TimeOfDay.php');
require_once(dirname(__FILE__, 2) . '/database/TimeOfDay.php');
require_once(dirname(__FILE__, 2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__, 1) . '/Api.php');

class TimeOfDay extends Api{

    private $model;

    function __construct(){
        parent::__construct();

        set_error_handler(array($this, 'error_handler'));
    }

    function insert() {
        try{
            $this->model = new \model\TimeOfDay();
            $this->model->setName($_GET['name']);

            $tijdvakStatement = new \database\TimeOfDay();
            $code = $tijdvakStatement->insert($this->model);
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

$timeOfDay = new TimeOfDay();
$timeOfDay->insert();
 ?>
