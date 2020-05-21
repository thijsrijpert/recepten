<?php
class User extends CRUD implements {
    function __construct($query = null){
        parent::__construct($query);

        $sql = "INSERT INTO User (username, password, token, role ) VALUES (:username, :password, :token, :role)";
        $this->stmt = Database::getConnection()->prepare($sql);
    }

    public function insert(model\Model $model) : String{
        try{
            $username = $model->getUsername();
            $password = $model->getPassword();
            $token = $model->getToken();
            $role = $model->getRole();
        }catch(ModelNullException $e){
            throw new NullPointerException($e);
        }

        $this->stmt->bindParam(':username', $username);
        $this->stmt->bindParam(':username', $password);
        $this->stmt->bindParam(':username', $token);
        $this->stmt->bindParam(':username', $role);
        $this->stmt->execute();

        return $this->stmt->errorCode();

    }
}
?>
