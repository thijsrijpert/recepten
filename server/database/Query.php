<?php
namespace database;
require_once(dirname(__FILE__, 1) . '/QueryParent.php');
class Query extends QueryParent{

    private $selectArguments = array();
    private $orderArguments = array();

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



    public function setOrderArguments(array $arguments = array()) : bool{
      $approvedArguments = $this->entity->getVariables();
      $this->orderArguments = array();

      if(count($arguments) != 0 && count($arguments) <= count($approvedArguments)){
          for($i = 0; $i <= count($arguments) - 1; $i++){
              for($k = 0; $k <= count($approvedArguments) - 1; $k++){
                  if(count($arguments[$i]) > 0){
                      if($arguments[$i][0] == $approvedArguments[$k][0]){
                          $this->orderArguments[$i][0] = $approvedArguments[$k][0];
                          $this->orderArguments[$i][1] = $this->getOrderOperator($arguments[$i][1]);
                      }
                  }else{
                      return false;
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



    public function getOrderOperator($operator) : String{
        if(\strtolower($operator) == ALLOWED_ORDER['asc']){
            return 'asc';
        }else if(\strtolower($operator) == ALLOWED_ORDER['desc']){
            return 'desc';
        }else{
            return 'asc';
        }
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
