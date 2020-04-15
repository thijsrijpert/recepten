<?php
require_once('../model/TijdvakModel.php');
require_once('../database/TijdvakStatement.php');
$tijdvak = new Tijdvak();
$tijdvak->insert();
class Tijdvak {

    private $model;

    function __construct(){

    }

    function insert() : void{
        $this->model = new TijdvakModel();
        $this->model->setName($_GET['name']);

        echo $_GET['name'];

        $tijdvakStatement = new TijdvakStatement();
        $tijdvakStatement->insert($this->model);

    }
}
 ?>
