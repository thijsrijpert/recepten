<?php
namespace database;
require_once(dirname(__FILE__, 2) . '/exception/NullPointerException.php');
class QueryBuilder{

    private $sql;
    private $query;

    public function __construct(Query $query){
        $this->query = $query;
    }

    public function generateSql() : bool{

        $selectArguments = $this->query->getSelectArguments();
        $whereArguments = $this->query->getWhereArguments();
        $orderArguments = $this->query->getOrderArguments();

        if($selectArguments > 0){
            $this->sql = "SELECT " . $selectArguments[0][0];

            if(count($selectArguments) > 1){
                for($i = 1; $i <= count($selectArguments) - 1; $i++){
                    $this->sql .= ", " . $selectArguments[$i][0];
                }
            }

            $this->sql .= " FROM " . substr(get_class($this->query->getEntity()), 6);
            if(count($whereArguments) > 0){
                $this->sql .= " WHERE " . $whereArguments[0][0] . ' ' . $whereArguments[0][1] . ' :' . $whereArguments[0][0];
                for($i = 1; $i <= count($whereArguments) - 1; $i++){
                    $this->sql .= ' AND ' . $whereArguments[$i][0] . ' ' . $whereArguments[$i][1] . ' :' . $whereArguments[$i][0];
                }
            }

            if(count($orderArguments) > 0){
                $this->sql .= " ORDER BY " . $orderArguments[0][0] . ' ' . $orderArguments[0][1];
                for($i = 1; $i <= count($whereArguments) - 1; $i++){
                    $this->sql .= ', ' . $orderArguments[$i][0] . ' ' . $orderArguments[$i][1];
                }
            }
        } else {
            return false;
        }

        return true;
    }

    public function getSql() : String{
        return $this->sql;
    }



    /**
     * Set the value of Query
     *
     * @param Query $query
     */
    public function setQuery(Query $query) : void
    {
        $this->query = $query;
    }



    /**
     * Get the value of Query
     *
     * @return mixed
     */
    public function getQuery()
    {
        return $this->query;
    }

}
?>
