<?php
namespace frontend\model;
require_once(dirname(__FILE__,2) . '/exception/ModelNullException.php');
require_once(dirname(__FILE__,1) . '/Model.php');
require_once(dirname(__FILE__,1) . '/Update.php');
  class Religion extends Model{
    private $name;
    private $id;

    public function __construct(String $name = null, int $id = null){
        $this->name = $name;
        $this->id = $id;
    }
    //the name of the religion
    public function getName() : String{
        return $this->name;
    }
    //the name of the religion
    public function setName(String $name){
        $this->name = $name;
    }
    //the id of the religion
    public function getId() : int{
        return $this->id;
    }
    //the id of the religion
    public function setId(int $id) {
        $this->id = $id;
    }
}
