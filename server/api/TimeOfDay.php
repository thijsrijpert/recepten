<?php
namespace api;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(-1);
require_once('../model/TimeOfDay.php');
require_once('../database/TimeOfDay.php');
require_once('../exception/NullPointerException.php');
require_once('Api.php');

class TimeOfDay extends Api{

    private $model;

    function __construct(){
        parent::__construct();

        set_error_handler('error_handler');
    }

    function insert() : void{
        try{
            $this->model = new \model\TimeOfDay();
            $this->model->setName($_GET['name']);

            $tijdvakStatement = new \database\TimeOfDay();
            $code = $tijdvakStatement->insert($this->model);

            $code = substr($code, 0, 2);

            parent::setHttpCode($code);
        }catch(\PDOException $e){
            echo $e->getCode();
            error_log($e->getCode());
            parent::setHttpCode($e->getCode());
        }catch(\exception\NullPointerException $e){
            header('HTTP/1.0 400 Bad Request');
        }
    }

    function error_handler(){
        throw new \exception\NullPointerException("Get value isn't passed");
    }
}

$timeOfDay = new TimeOfDay();
$timeOfDay->insert();
 ?>
