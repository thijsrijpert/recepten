<?php
require_once('../model/TijdvakModel.php');
require_once('../database/TijdvakStatement.php');
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
        }catch(PDOException $e){
            $e->getCode();
            parent::setHttpCode($e->getCode());

        }


        $code = substr($code, 0, 2);

        parent::setHttpCode($code);
    }
}

$tijdvak = new Tijdvak();
$tijdvak->insert();
 ?>
