<?php
require_once('../model/ReceptModel.php');
require_once('../model/ReceptStatement');
require_once('Api.php');

class Recept extends Api{

  private $model;

  function __construct(){

  }

  function insert() : void{
    $this->model = new ReceptModel();
    $this->model->setId($_GET['id']);
    $this->model->setName($_GET['name']);
    $this->model->setDescription($_GET['description']);
    $this->model->setLandcode($_GET['landcode']);
    $this->model->setUsername($_GET['username']);



    echo $_GET['id'];
    echo $_GET['name'];
    echo $_GET['description'];
    echo $_GET['landcode'];
    echo $_GET['username'];
    $code = null;
    try{
      $receptStatement = new ReceptStatement();
      $receptStatement->insert($this->model);
    }catch(PDOException $e){
      $e->getCode();
      parent::setHttpCode($e->getCode());
    }


    $code = substr($code, 0, 2);

    parent::setHttpCode($code);
  }
}

$recept = new Recept();
$recept->insert();

 ?>
