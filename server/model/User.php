<?php
namespace model;
require_once(dirname(__FILE__, 1) . '/Model.php');
require_once(dirname(__FILE__, 2) . '/exception/ModelNullException.php');
class User extends Model{
    private $username;
    private $password;
    private $userRole;
    private $token;
    private $salt;
    private $iteration;

    public function __construct(String $username = null, String $password = null, String $token = null, String $role = null, int $salt = null, int $iteration = null){
        $this->username = $username;
        $this->password = $password;
        $this->userRole = $userRole;
        $this->token = $token;
        $this->salt = $salt;
        $this->iteration = $iteration;
    }

    public function getUsername() : String {
        if($this->username != null){
              return $this->username;
        }
        throw new \exception\ModelNullException("Username is not set");
    }

    public function setUsername(String $username) {
        $this->username = $username;
    }


    public function getPassword() : String
    {
      if($this->password != null){
            return $this->password;
      }
      throw new \exception\ModelNullException("Password is not set");
    }

    public function setPassword(String $password)
    {
        $this->password = $password;
    }

    public function getRole() : String
    {
      if($this->userRole != null){
            return $this->userRole;
      }
      throw new \exception\ModelNullException("Role is not set");
    }

    public function setRole(String $userRole)
    {
        $this->userRole = $userRole;
    }

    public function getToken() : String
    {
      if($this->token != null){
            return $this->token;
      }
      throw new \exception\ModelNullException("Role is not set");
    }

    public function setToken(String $token)
    {
        $this->token = $token;
    }


        public function getSalt() : int
        {
            if($this->salt != null){
                return $this->salt;
            }
            throw new \exception\ModelNullException("Salt is not set");
        }


        public function setSalt(int $salt)
        {
            $this->salt = $salt;
        }

        /**
         * Get the value of Iteration
         *
         * @return mixed
         */
        public function getIteration() : int
        {
            if($this->iteration !== null){
                return $this->iteration;
            }
            throw new \exception\ModelNullException("Iteration is not set");

        }

        public function setIteration(int $iteration)
        {
            $this->iteration = $iteration;
        }


    //get the columns this entity has
    public function getVariables(){
        return [['username'], ['password'], ['token'], ['role'], ['salt'], ['iteration']];
    }
    //return the object to the UI
    public function jsonSerialize() {
        $vars = get_object_vars($this);
        $vars['password'] = null;
        $vars['username'] = null;
        $vars['salt'] = null;
        $vars['iteration'] = null;
        $json = parent::setUpJson($vars);
        return $json;
    }


}
?>
