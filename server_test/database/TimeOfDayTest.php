<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) .'\server\model\TimeOfDay.php');
require_once(dirname(__FILE__, 3) . '\server\database\TimeOfDay.php');
require_once(dirname(__FILE__, 3) . '\server\database\QueryBuilder.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');

final class TimeOfDayDatabaseTest extends TestCase
{
    protected $mock;
    protected $mockUpdate;
    protected $timeOfDay;

    public function setUp() : void{
        $this->mock = $this->createMock('database\QueryBuilder');
        $this->mockUpdate = $this->createMock('database\QueryBuilderUpdate');
        $this->timeOfDay = new database\TimeOfDay();
    }

    public function testSelect(): void
    {
      $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM TimeOfDay WHERE name = :name"));
      $this->timeOfDay = new database\TimeOfDay($this->mock);
        $this->assertEquals(
            array('00000', array(array(new \model\TimeOfDay('Diner')))),
            $this->timeOfDay->select(new \model\TimeOfDay('Diner'))
        );
    }

    public function testSelectNoWhere(): void
    {
      $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM TimeOfDay"));
      $this->timeOfDay = new database\TimeOfDay($this->mock);
        $this->assertEquals(
            array('00000', array(array(new model\TimeOfDay('Diner'), new model\TimeOfDay('Lunch'), new model\TimeOfDay('Ontbijt')))),
            $this->timeOfDay->select(new \model\TimeOfDay())
        );
    }

    public function testUpdate(): void
    {
      $this->mockUpdate->expects($this->any())->method('getSql')->will($this->returnValue("UPDATE TimeOfDay SET name = :nameUpdate WHERE name = :name"));
      $this->timeOfDay = new database\TimeOfDay($this->mockUpdate);
        $this->assertEquals(
            '00000',
            $this->timeOfDay->update(new \model\TimeOfDay('What about second breakfast?!'), new \model\TimeOfDay('Ontbijt'))
        );
    }

    public function testInsert(): void
    {

        $this->assertEquals(
            '00000',
            $this->timeOfDay->insert(new \model\TimeOfDay('Or supper'))

        );
        $this->assertEquals(
            '23000',
            $this->timeOfDay->insert(new \model\TimeOfDay('Or supper'))

        );
        // $this->assertEquals(
        //     '22001',
        //     $this->timeOfDay->insert(new \model\TimeOfDay('THHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSSSSSSSSSSSIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSLLLLLLONNNNNNG')),
        //
        // );
    }
}
