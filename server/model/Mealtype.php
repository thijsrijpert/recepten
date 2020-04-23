<?php
namespace model;
class Mealtype {
    private $name;

    public function getName() : String{
        return $this->name;
    }

    public function setName(String $name){
        $this->name = $name;
    }
}
?>
