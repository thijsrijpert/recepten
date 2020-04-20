<?php
namespace api;
require_once('../model/Religion.php');
require_once('../database/Religion.php');
require_once('../exception/NullPointerException.php');
require_once('Api.php');

class Religion extends Api{

    private $model;

    function __construct(){
        parent::__construct();
        //set_error_handler('error_handler');
    }

    function insert() : void{
        try{
            $this->model = new model\Religion($_GET['name']);

            $religieStatement = new database\Religion();
            $code = $religieStatement->insert($this->model);

            $code = substr($code, 0, 2);

            parent::setHttpCode($code);

        }catch(\PDOException $e){
            parent::setHttpCode($e->getCode());
        }catch(exception\NullPointerException $e){
            header('HTTP/1.0 400 Bad Request');
        }
    }

    function error_handler(){
        throw new exception\NullPointerException("Get value isn't passed");
    }
}

$religie = new Religie();
$religie->insert();
?>
