<?php
namespace model;
require_once(dirname(__FILE__,1) . '/Model.php');
require_once(dirname(__FILE__,1) . '/Update.php');
require_once(dirname(__FILE__,2) . '/exception/ModelNullException.php');
class Country extends \model\Model implements \model\Model\Update{
    private $countrycode;
    private $name;
    private $description;

    public function __construct(String $countrycode = null ,String $name = null ,String $description = null){
        $this->countrycode = $countrycode;
        $this->name = $name;
        $this->description = $description;
    }

    public function getCountrycode() : String{
        if($this->countrycode !== null){
            return $this->countrycode;
        }
        throw new \exception\ModelNullException("The value countrycode is null");
    }

    public function setCountrycode(String $countrycode){
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

    public function getUpdateVariables() : array{
        return [['countrycode'], ['name'], ['description']];
    }

    public function jsonSerialize(){
      $json_countrycode = '"countrycode" : ' . '"' . $this->countrycode . '"';
      $json_name = '"name" : ' . '"' . $this->name . '"';
      $json_description = '"description" : ' . '"' . $this->description . '"';
      $final_string = "{";

      if($this->countrycode != null){
        $final_string .= $json_countrycode;
      }

      if($this->name != null){
        if($this->countrycode !== null){
          $final_string .= ",";
        }
        $final_string .= $json_name;
      }

      if($this->description != null){
        if($this->name !== null || $this->countrycode !== null){
          $final_string .= ",";
        }
        $final_string .= $json_description;
      }

      $final_string .= "}";

      return \json_decode($final_string);

    }

}
