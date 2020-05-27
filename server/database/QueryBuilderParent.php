<?php
namespace database;
abstract class QueryBuilderParent{
    protected $sql;
    protected $query;
    protected function __construct(QueryParent $query){

    }
    public abstract function generateSql() : bool;

    public function generateWhere() : bool {
        $whereArguments = $this->query->getWhereArguments();
        if(count($whereArguments) > 0){
            $this->sql .= " WHERE " . $whereArguments[0][0] . ' ' . $whereArguments[0][1] . ' :' . $whereArguments[0][0];
            for($i = 1; $i <= count($whereArguments) - 1; $i++){
                $this->sql .= ' AND ' . $whereArguments[$i][0] . ' ' . $whereArguments[$i][1] . ' :' . $whereArguments[$i][0];
            }
        }else{
            return false;
        }
        return true;
    }

    public function getComparisonOperator($operator) : String{
       if($operator == ALLOWED_COMPARISON['<']){
           return '<';
       }else if($operator == ALLOWED_COMPARISON['=']){
           return '=';
       }else if($operator == ALLOWED_COMPARISON['>']){
           return '>';
       }
       throw new \exception\NullPointerException("The request send had an illigal operator");
   }

    public function getSql() : String{
        return $this->sql;
    }

    /**
     * Set the value of Query
     *
     * @param Query $query
     */
    public function setQuery(QueryParent $query)
    {
        $this->query = $query;
    }



    /**
     * Get the value of Query
     *
     */
    public function getQuery()
    {
        return $this->query;
    }
}
 ?>
