<?php
namespace frontend\connector;
    interface IHoofdverzameling {
        public function select() : String;
        public function update(\model\Model $old, \model\Model $new) : String;
        public function insert(\model\Model $model) : String;
    }
?>
