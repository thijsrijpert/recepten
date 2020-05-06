<?php
namespace model;

require_once(dirname(__FILE__,1) . '/Model.php');
  class Religion extends \model\Model{
    private $name;
    private $id;

    public function __construct(String $name = null, int $id = null){
        $this->name = $name;
        $this->id = $id;
    }

    public function getName() : ?String{
        return $this->name;
    }

    public function setName(String $name){
        $this->name = $name;
    }

    public function getId() : ?int{
        return $this->id;
    }

    public function setId(int $id) : void{
        $this->id = $id;
    }

    public function getVariables(){
        return [['name'], ['id']];
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
