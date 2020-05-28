<?php
namespace model;
require_once(dirname(__FILE__,2) . '/exception/ModelNullException.php');
require_once(dirname(__FILE__,1) . '/Model.php');
require_once(dirname(__FILE__,1) . '/Update.php');
  class Religion extends \model\Model implements \model\Update{
    private $name;
    private $id;

    public function __construct(String $name = null, int $id = null){
        $this->name = $name;
        $this->id = $id;
    }
    //the name of the religion
    public function getName() : String{
        if($this->name !== null){
            return $this->name;
        }
        throw new \exception\ModelNullException("The value name is null");
    }
    //the name of the religion
    public function setName(String $name){
        $this->name = $name;
    }
    //the id of the religion
    public function getId() : int{
        if($this->id !== null){
            return $this->id;
        }
        throw new \exception\ModelNullException("The value id is null");
    }
    //the id of the religion
    public function setId(int $id) {
        $this->id = $id;
    }
    //all columns of this enity
    public function getVariables(){
        return [['name'], ['id']];
    }

    //all updatable columns of this enity
    public function getUpdateVariables() : array{
        return [['name']];
    }


    public function jsonSerialize() {
        if($this->name != null && $this->id != null){
          return [
              'name' => $this->name,
              'id' => $this->id,
          ];
        }else if($this->name == null){
            return [
                'id' => $this->id,
            ];
        }else {
            return [
                'name' => $this->name,
            ];
        }
    }
  }

  class ReligionPDO extends Religion{
      public function __construct(String $name, int $id){

      }
  }
