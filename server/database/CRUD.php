<?php
namespace database;
    abstract class CRUD {
        protected $select = array();
        protected $stmt;
        protected $update;
        protected $delete;

        public function __construct(Query $query){
            addSelectStatement($query);
        }

        abstract function select() : String;

        abstract function insert(Model $model) : String;

        public function addSelectStatement(Query $query){
            if($query->generateSql()){
                $select[] = Database::getConnection()->prepare($query->getSql());
            }
        }
    }
?>
