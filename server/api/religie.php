<?php
require_once('../model/ReligieStatement.php');
require_once('../database/ReligieStatement.php');
require_once('Api.php');

class Religie extends Api{

    private $model;

    function __construct(){

    }

    function insert() : void{
        $this->model = new ReligieModel();
        $this->model->setName($_GET['name']);

        echo $_GET['name'];
        $code = null;
        try{
          $religieStatement = new ReligieStatement();
          $code = $religieStatement->insert($this->model);
        }catch(PDOException $e){
            $e->getCode();
            parent::setHttpCode($e->getCode());
        }

        $code = substr($code, 0, 2);

        parent::setHttpCode($code);
    }
}

$religie = new Religie();
$religie->insert();
?>
