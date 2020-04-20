<?php
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(-1);
echo 'Test';
require_once('../model/TijdvakModel.php');
echo 'Test2';
require_once('../database/TijdvakStatement.php');
echo 'Test3';
require_once('Api.php');

class Tijdvak extends Api{

    private $model;

    function __construct(){

    }

    function insert() : void{

        $this->model = new TijdvakModel();
        $this->model->setName($_GET['name']);

        echo $_GET['name'];
        $code = null;
        try{
          $tijdvakStatement = new TijdvakStatement();
          $code = $tijdvakStatement->insert($this->model);
          error_log($code);
        }catch(PDOException $e){
            echo $e->getCode();
            error_log($e->getCode());
            parent::setHttpCode($e->getCode());

        }
        echo $code;

        $code = substr($code, 0, 2);

        parent::setHttpCode($code);
    }
}
echo 'Test5';

$tijdvak = new Tijdvak();
$tijdvak->insert();
 ?>
