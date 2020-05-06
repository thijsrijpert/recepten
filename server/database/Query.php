<?php
namespace database;
require_once(dirname(__FILE__, 2) . '/exception/NullPointerException.php');
class Query{

    private $selectArguments = array();
    private $whereArguments = array();
    private $orderArguments = array();
    private $entity;

    public function __construct(\model\Model $entity){
        $this->entity = $entity;
    }

    public function setSelectArguments($arguments = array()) : bool{
        $approvedArguments = $this->entity->getVariables();

        $this->selectArguments = array();
        if(count($arguments) == 0){
          $this->selectArguments[0][0] = '*';
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
        if(\strtolower($operator) == ALLOWED_ORDER['asc']){
            return 'asc';
        }else if(\strtolower($operator) == ALLOWED_ORDER['desc']){
            return 'desc';
        }else{
            return 'asc';
        }
    }

    public function getEntity() : \model\Model{
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
