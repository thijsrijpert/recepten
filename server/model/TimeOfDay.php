<?php
namespace model;
class TimeOfDay {
    private $name;

    public function getName() : String{
        return $this->name;
    }

    public function setName(String $name){
        echo $name;
        $this->name = $name;
        echo $this->name;
    }
}
?>
