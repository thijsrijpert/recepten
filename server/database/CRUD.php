<?php
namespace database;
    abstract class CRUD {
        protected $select = array();
        protected $stmt;
        protected $update;
        protected $delete;

        public function __construct(QueryBuilder $query){
            $this->addSelectStatement($query);
        }

        abstract function select(\model\Model $model) : String;

        abstract function insert(\model\Model $model) : String;

        public function addSelectStatement(QueryBuilder $query){
            if($query->generateSql()){
                $select[] = Database::getConnection()->prepare($query->getSql());
            }
        }
    }
?>
