<?php
namespace model;
require_once(dirname(__FILE__,1) . '/Model.php');
class Country extends \model\Model{
    private $countrycode;
    private $name;
    private $description;

    public function __construct(Int $countrycode = null ,Int $name = null ,String $description = null){
        $this->countrycode =$countrycode;
        $this->name = $name;
        $this->description = $description;
    }

    public function getCountrycode() : int{
        if($this->countrycode !== null){
            return $this->countrycode;
        }
        throw new \exception\ModelNullException("The value countrycode is null");
    }

    public function setCountrycode(Int $countrycode){
      $this->countrycode = $countrycode;
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

    public function getDescription() : String{
        if($this->description !== null){
            return $this->description;
        }
        throw new \exception\ModelNullException("The value description is null");
    }

    public function setDescription(String $description){
      $this->description = $description;
    }

    public function getVariables(){
        return [['countrycode'], ['name'], ['description']];
    }

    public function jsonSerialize(){
      $json_name = "'name' => $this->name,";
      $json_countrycode = "'countrycode' => $this->countrycode,";
      $json_description = "'description' => $this->description,";
      $final_string = "[";

      if($json_countrycode != null){
        $final_string .= $json_countrycode;
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
