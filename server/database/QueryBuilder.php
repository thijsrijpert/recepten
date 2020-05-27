<?php
namespace database;
require_once(dirname(__FILE__, 2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__, 1) . '/QueryBuilderParent.php');
class QueryBuilder extends QueryBuilderParent{

    public function __construct(Query $query){
        $this->query = $query;
    }

    public function generateSql() : bool{
        if($this->generateSelect()){
            $this->generateFrom();
            parent::generateWhere();
            $this->generateOrder();
        }else{
            $this->sql = "";
            return false;
        }
        return true;
    }

    public function generateSelect() : bool{
        $selectArguments = $this->query->getSelectArguments();
        if(count($selectArguments) > 0){
            $this->sql = "SELECT " . $selectArguments[0][0];

            if(count($selectArguments) > 1){
                for($i = 1; $i <= count($selectArguments) - 1; $i++){
                    $this->sql .= ", " . $selectArguments[$i][0];
                }
            }
        }else{
            return false;
        }
        return true;
    }

    public function generateOrder() {
        $orderArguments = $this->query->getOrderArguments();
        if(count($orderArguments) > 0){
            $this->sql .= " ORDER BY " . $orderArguments[0][0] . ' ' . $orderArguments[0][1];
            for($i = 1; $i <= count($orderArguments) - 1; $i++){
                $this->sql .= ', ' . $orderArguments[$i][0] . ' ' . $orderArguments[$i][1];
            }
        }
    }

    public function generateFrom(){
        $this->sql .= " FROM " . substr(get_class($this->query->getEntity()), 6);
    }

}
?>
