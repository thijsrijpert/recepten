<?php
namespace model;
require_once(dirname(__FILE__,1) . '/Model.php');
class TimeOfDay extends \model\Model{
  //the name of the dish time, eg. lunch, brunch, diner
    private $name;

    public function __construct(String $name = null){
        $this->name = $name;
    }
    //the name of the dish time, eg. lunch, brunch, diner
    public function getName() : String{
        if($this->name !== null){
            return $this->name;
        }
        throw new \exception\ModelNullException("The value name is null");
    }
    //the name of the dish time, eg. lunch, brunch, diner
    public function setName(String $name){
        $this->name = $name;
    }
    //get the columns this entity has
    public function getVariables(){
        return [['name']];
    }
    //return the object to the UI
    public function jsonSerialize() {
        if($this->name != null){
          return [
              'name' => $this->name,
          ];
        }else{
            return [];
        }
    }
}
?>
