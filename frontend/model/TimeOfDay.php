<?php
namespace model;
require_once(dirname(__FILE__,1) . '/Model.php');
class TimeOfDay extends \model\Model {
  //the name of the dish time, eg. lunch, brunch, diner
    private $name;

    public function __construct(String $name = null){
        $this->name = $name;
    }
    //the name of the dish time, eg. lunch, brunch, diner
    public function getName() : String{
        return $this->name;
    }
    //the name of the dish time, eg. lunch, brunch, diner
    public function setName(String $name){
        $this->name = $name;
    }
}
?>
