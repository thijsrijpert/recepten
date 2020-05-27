<?php
namespace model;
require_once(dirname(__FILE__,1) . '/Model.php');
require_once(dirname(__FILE__,2) . '/exception/ModelNullException.php');
class Mealtype extends \model\Model{
    private $name;

    public function __construct(String $name = null){
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
      $json_name = '"name" : ' . '"' . $this->name . '"';
      $final_string = "{";

      if($this->name != null){
        $final_string .= $json_name;
      }

      $final_string .= "}";
      return \json_decode($final_string);
    }
}
