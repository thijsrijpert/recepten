<?php
namespace model;
class Mealtype {
    private $name;

    public function __construct(Int $name = null){
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
