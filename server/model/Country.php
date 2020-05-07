<?php
namespace model;
require_once(dirname(__FILE__,1) . '/Model.php');
class Country extends \model\Model{
    private $country_code;
    private $name;
    private $description;

    public function __construct(Int $country_code = null ,Int $name = null ,String $description = null){
        $this->country_code =$country_code;
        $this->name = $name;
        $this->description = $description;
    }

    public function getCountry_code() : int{
        if($this->country_code != null){
            return $this->country_code;
        }
        throw new \exception\ModelNullException("The value country_code is null");
    }

    public function setCountry_code(Int $country_code){
      $this->country_code = $country_code;
    }

    public function getName() : String{
        if($this->name != null){
            return $this->name;
        }
        throw new \exception\ModelNullException("The value name is null");
    }

    public function setName(String $name){
        $this->name = $name;
    }

    public function getDescription() : String{
        if($this->description != null){
            return $this->description;
        }
        throw new \exception\ModelNullException("The value description is null");
    }

    public function setDescription(String $description){
      $this->description = $description;
    }

    public function getVariables(){
        return [['country_code'], ['name'], ['description']];
    }

    public function jsonSerialize(){
      $json_name = "'name' => $this->name,";
      $json_country_code = "'country_code' => $this->country_code,";
      $json_description = "'description' => $this->description,";
      $final_string = "[";

      if($json_country_code != null){
        $final_string .= $json_country_code;
      }

      if($json_description != null){
        $final_string .= $json_description;
      }

      if($json_name != null){
        $final_string .= $json_name;
      }

      $final_string .= "]";
      return \json_encode($final_string);

    }

}
