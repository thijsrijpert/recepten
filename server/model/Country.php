<?php
namespace model;
class Country {
    private $country_code;
    private $name;
    private $description;

    public function __construct(Int $country_code ,Int $name ,String $description = null){
        $this->country_code =$country_code;
        $this->name = $name;
        $this->description = $description;
    }

    public function getCountry_code() : Int{
      return $this->country_code;
    }

    public function setCountry_code(Int $country_code){
      $this->country_code = $country_code;
    }

    public function getName() : String{
        return $this->name;
    }

    public function setName(String $name){
        $this->name = $name;
    }

    public function getDescription() : String{
      return $this->description;
    }

    public function setDescription(String $description){
      $this->description = $description;
    }

}
?>
