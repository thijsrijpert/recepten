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

    public function getName() : String{
        if($this->name !== null){
            return $this->name;
        }
        throw new \exception\ModelNullException("The value name is null");
    }

    public function setName(String $name){
        $this->name = $name;
    }

    public function getId() : int{
        if($this->name !== null){
            return $this->id;
        }
        throw new \exception\ModelNullException("The value id is null");
    }

    public function setId(int $id) {
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
