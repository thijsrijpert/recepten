<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__,3) .'\server\model\TimeOfDay.php');
require_once(dirname(__FILE__, 3) . '\server\database\TimeOfDay.php');

final class TimeOfDayTest extends TestCase
{

    protected $timeOfDay;

    public function setUp() : void{
        $this->timeOfDay = new database\TimeOfDay();
    }

    // public function dataProvider(){
    //     return [
    //         [, '00000'],
    //         [new \model\TimeOfDay('Dit is een test'), '23000'],
    //         [, '22001'],
    //     ];
    // }

    public function testInsert(): void
    {
        $this->assertEquals(
            '00000',
            $this->timeOfDay->insert(new \model\TimeOfDay('Dit is een test'))

        );
        $this->assertEquals(
            '23000',
            $this->timeOfDay->insert(new \model\TimeOfDay('Dit is een test'))

        );
        $this->assertEquals(
            '22001',
            $this->timeOfDay->insert(new \model\TimeOfDay('WAYYYYYYYYYYYYYTOOOOOOOOOOOOOOMAAAAAAAAAAAAANNNNYYYYYYYYYYYYYYCHAARRRRRRRRRRRRRRRRRSSSSSSSSSSS')),

        );
    }
}
