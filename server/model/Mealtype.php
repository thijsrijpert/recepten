<?php
namespace model;
require_once(dirname(__FILE__,1) . '/Model.php');
class Mealtype extends \model\Model{
    private $name;

    public function __construct(Int $name = null){
        $this->name = $name;
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

    public function getVariables(){
        return [['name']];
    }

    public function jsonSerialize() {
      if(this->name != null){
        return[
          'name' => $this->name,
        ];
      }else{
        return[
          'name' => $this->name,
        ];
      }
    }
}
