<?php
namespace database;
    abstract class CRUD {
        protected $select = array();
        protected $stmt;
        protected $update;
        protected $delete;

        public function __construct(QueryBuilder $query = null){
            if(isset($query)){
                $this->addSelectStatement($query);
            }

            set_error_handler(array($this, 'error_handler'));
        }

        abstract function select(\model\Model $model) : array;

        abstract function insert(\model\Model $model) : String;

        public function addSelectStatement(QueryBuilder $query){
            if($query->getSql() != null || $query->generateSql()){
                $this->select[] = Database::getConnection()->prepare($query->getSql());
            }
        }



        abstract function error_handler($errno, $errstr, $errfile, $errline);
    }
?>
