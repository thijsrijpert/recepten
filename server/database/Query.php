<?php
namespace database;
require_once(dirname(__FILE__, 2) . '/exception/NullPointerException.php');
class Query{

    private $sql;
    private $selectArguments = array();
    private $whereArguments;
    private $orderArguments;
    private $entity;

    public function __construct(\model\Model $entity){
        $this->entity = $entity;
    }

    public function setSelectArguments($arguments = array()) : bool{
        $approvedArguments = $this->entity->getVariables();

        $this->selectArguments = array();
        if(count($arguments) == 0){
          $this->selectArguments[0] = '*';
          return true;
        } else if(count($arguments) <= count($approvedArguments)){
            for($i = 0; $i <= count($arguments) - 1; $i++){
                for($k = 0; $k <= count($approvedArguments) - 1; $k++){
                    if($arguments[$i][0] == $approvedArguments[$k][0]){
                        $this->selectArguments[$i][0] = $approvedArguments[$k][0];
                    }
                }
            }
        }

        if($arguments === $this->selectArguments && $this->selectArguments != array()){
            return true;
        }

        $this->selectArguments = array();
        return false;
    }

    public function setWhereArguments(array $arguments = array()) : bool{
      $approvedArguments = $this->entity->getVariables();
      $this->orderArguments = array();

      if(count($arguments) != 0 && count($arguments) <= count($approvedArguments)){
          for($i = 0; $i <= count($arguments) - 1; $i++){
              for($k = 0; $k <= count($approvedArguments) - 1; $k++){
                  if($arguments[$i][0] == $approvedArguments[$k][0]){
                      $this->whereArguments[$i][0] = $approvedArguments[$k][0];
                      $this->whereArguments[$i][1] = $this->getComparisonOperator($arguments[$i][1]);
                  }
              }
          }
      }

      if(count($arguments) === count($this->whereArguments)){
          return true;
      }
      $this->whereArguments = array();
      return false;
    }

    public function setOrderArguments(array $arguments = array()) : bool{
      $approvedArguments = $this->entity->getVariables();
      $this->orderArguments = array();

      if(count($arguments) != 0 && count($arguments) <= count($approvedArguments)){
          for($i = 0; $i <= count($arguments) - 1; $i++){
              for($k = 0; $k <= count($approvedArguments) - 1; $k++){
                  if($arguments[$i][0] == $approvedArguments[$k][0]){
                      $this->orderArguments[$i][0] = $approvedArguments[$k][0];
                      $this->orderArguments[$i][1] = $this->getOrderOperator($arguments[$i][1]);
                  }
              }
          }
      }

      if(count($arguments) === count($this->orderArguments)){
          return true;
      }
      $this->orderArguments = array();
      return false;
    }

    public function generateSql() : bool{
      echo 'executed';
        if($this->selectArguments > 0){
            $this->sql = "SELECT " . $this->selectArguments[0];

            if(count($this->selectArguments) > 1){
                foreach($this->selectArguments as $value){
                    $this->sql .= ", " . $this->selectArguments[1];
                }
            }

            $this->sql .= " FROM " . $this->entity->getVariables();
            if(count($this->whereArguments) > 0){
                $this->sql .= " WHERE ";
                foreach($this->whereArguments as $value){
                    $this->sql .= $value[0] . ' ' . $value[1] . ' :' . $value[0];
                }
            }

            if(count($this->orderArguments) > 0){
                $this->sql .= " ORDER BY ";
                foreach($this->orderArguments as $value){
                    $this->sql .= $value[0] . ' ' . $value[1];
                }
            }
        } else {
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

    public function getOrderOperator($operator) : String{
        if(\strtolower($operator) == ALLOWED_ORDER['desc']){
            return 'desc';
        }else if(\strtolower($operator) == ALLOWED_ORDER['asc']){
            return 'asc';
        }
        throw new \exception\NullPointerException("The request send had an illigal operator");
    }

    public function getSql() : String{
        return $this->sql;
    }

    public function getEntity() : Model{
        return $this->entity;
    }



    /**
     * Get the value of Select Arguments
     *
     * @return mixed
     */
    public function getSelectArguments()
    {
        return $this->selectArguments;
    }

    /**
     * Get the value of Where Arguments
     *
     * @return mixed
     */
    public function getWhereArguments()
    {
        return $this->whereArguments;
    }

    /**
     * Get the value of Order Arguments
     *
     * @return mixed
     */
    public function getOrderArguments()
    {
        return $this->orderArguments;
    }

}
?>
