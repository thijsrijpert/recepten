<?php
  class ReligieModel {
    private $name;
    private $id;

    public function __construct(String $name, int $id = null){
        $this->name = $name;
        $this->id = $id;
    }

    public function getName() : String{
        return $this->name;
    }

    public function setName(String $name){
        $this->name = $name;
    }

    public function getId() : int{
        return $this->id;
    }

    public function setId(int $id) : void{
        $this->id = $id;
    }
  }
