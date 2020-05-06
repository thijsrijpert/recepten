<?php
namespace model;
class TimeOfDay {
    private $name;

    public function __construct(String $name){
        $this->name = $name;
    }

    public function getName() : String{
        return $this->name;
    }

    public function setName(String $name){
        $this->name = $name;
    }
}
?>
