<?php
namespace frontend\connector;
require_once('Connector.php');
require_once('IHoofdverzameling.php');
class TimeOfDay extends Connector implements IHoofdverzameling{
    public function __construct(){
    }

    public function select() : String{
        return parent::get("https://beroepsproduct.rijpert-webdesign.nl/api/timeofday.php");
    }

    public function update(\model\Model $old, \model\Model $new) : String{
        return parent::get("https://beroepsproduct.rijpert-webdesign.nl/api/timeofday.php?set=name-" . $new->getName() . "&where=name-eq-" . $old->getName());
    }

    public function insert(\model\Model $model) : String{
        $encodedName = str_replace ( ' ', '%20', $model->getName());
        return parent::get("https://beroepsproduct.rijpert-webdesign.nl/api/timeofday.php?name=" . $encodedName);
    }
}
?>
