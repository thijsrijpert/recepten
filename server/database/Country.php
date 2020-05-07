<?php
namespace database;
require_once(dirname(__FILE__,1) . '/Database.php');
require_once(dirname(__FILE__,1) . '/CRUD.php');
require_once(dirname(__FILE__,2) . '/model/Country.php');
  class Country extends CRUD{

    function __construct(QueryBuilder $query = null){
          $sql = "INSERT INTO Country (country_code, name, description) VALUES (:country_code, :name, :description)";
          $this->stmt = \database\Database::getConnection()->prepare($sql);

          parent::__construct($query);
    }

      function insert(\model\Model $model) : String {
          $country_code = $model->getCountry_code();
          $name = $model->getName();
          $description = $model->getDescription();
          $this->stmt->bindParam(':country_code', $country_code);
          $this->stmt->bindParam(':name', $name);
          $this->stmt->bindParam(':description', $description);
          $this->stmt->execute();

          return $this->stmt->errorCode();
      }

      function select(\model\Model $model) : array{
        if(null != $model->getCountry_code()){
          $this->select[0]->bindParam(':name', $model->getCountry_code());
        }
        if(null != $model->getName()){
          $this->select[0]->bindParam(':name', $model->getName());
        }
        if(null != $model->getDescription()){
          $this->select[0]->bindParam(':description', $model->getDescription());
        }

        $this->select[0]->execute();

        $results = $this->select[0]->fetchAll(\PDO::FETCH_CLASS|\PDO::FETCH_PROPS_LATE, 'model\Country');

        return array($this->select[0]->errorCode(), array($results));


      }

      function error_handler($errno, $errstr, $errfile, $errline){

      }
  }
?>
