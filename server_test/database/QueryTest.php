<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');
require_once(dirname(__FILE__, 3) . '\server\database\Query.php');


final class QueryTest extends TestCase
{

    protected $query;

    public function setUp() : void{
        $this->query = new database\Query(new \model\Religion());
    }

    public function testSetSelectArguments(): void
    {
        $good_input = [['name'], ['id']];
        $this->assertEquals(
            true,
            $this->query->setSelectArguments($good_input)
        );
        $this->assertEquals(
            $good_input,
            $this->query->getSelectArguments()
        );
    }

    public function testSetSelectArgumentsBadArguments(): void
    {
        $good_input = [['illigal_column_name'], ['id']];
        $this->assertEquals(
            false,
            $this->query->setSelectArguments($good_input)
        );
        var_dump($this->query->getSelectArguments());
        $this->assertEquals(
            array(),
            $this->query->getSelectArguments()
        );
    }

    public function testSetSelectArgumentsNoArguments(): void
    {
        $good_input = null;
        $this->assertEquals(
            true,
            $this->query->setSelectArguments($good_input)
        );
        $this->assertEquals(
            ['*'],
            $this->query->getSelectArguments()
        );
    }

    public function testSetWhereArguments(): void
    {
        $good_input = [['name', 'eq'], ['id', 'gr']];
        $good_output = [['name', '='], ['id', '>']];
        $this->assertEquals(
            true,
            $this->query->setWhereArguments($good_input)
        );
        $this->assertEquals(
            $good_output,
            $this->query->getWhereArguments()
        );
    }

    public function testSetWhereArgumentsBadArguments(): void
    {
        $bad_input = [['illigal_column_name', 'eq'], ['id', 'eq']];
        $this->assertEquals(
            false,
            $this->query->setWhereArguments($bad_input)
        );

        $this->assertEquals(
            array(),
            $this->query->getWhereArguments()
        );
    }

    public function testSetWhereArgumentsIlligalOperator(): void
    {
        $bad_input = [['name', 'not_existing_operator'], ['id', 'eq']];
        $this->expectException(\exception\NullPointerException::class);
        $this->assertEquals(
            false,
            $this->query->setWhereArguments($bad_input)
        );
        $this->assertEquals(
            array(),
            $this->query->getWhereArguments()
        );
    }

    public function testSetWhereArgumentsTooManyColumns(): void
    {
        $bad_input = [['name', 'gr'], ['id', 'eq'], ['name', 'gr']];
        $this->assertEquals(
            false,
            $this->query->setWhereArguments($bad_input)
        );
        $this->assertEquals(
            array(),
            $this->query->getWhereArguments()
        );
    }

    public function testSetWhereArgumentsValidFewerColumns(): void
    {
        $good_input = [['name', 'gr']];
        $good_output = [['name', '>']];
        $this->assertEquals(
            true,
            $this->query->setWhereArguments($good_input)
        );
        $this->assertEquals(
            $good_output,
            $this->query->getWhereArguments()
        );
    }

    public function testSetOrderArguments(): void
    {
        $good_input = [['id', 'desc'], ['name', 'asc']];
        $good_output = [['id', 'desc'], ['name', 'asc']];
        $this->assertEquals(
            true,
            $this->query->setOrderArguments($good_input)
        );
        $this->assertEquals(
            $good_output,
            $this->query->getOrderArguments()
        );
    }

    public function testSetOrderArgumentsBadArguments(): void
    {
        $input = [['illigal_column_name', 'desc'], ['name', 'asc']];
        $output = array();
        $this->assertEquals(
            false,
            $this->query->setOrderArguments($input)
        );
        $this->assertEquals(
            $output,
            $this->query->getOrderArguments()
        );
    }

    public function testSetOrderArgumentsIlligalOperator(): void
    {
        $input = [['name', 'illigal_operator'], ['id', 'asc']];
        $output = array();
        $this->expectException(\exception\NullPointerException::class);
        $this->assertEquals(
            false,
            $this->query->setOrderArguments($input)
        );
        $this->assertEquals(
            $output,
            $this->query->getOrderArguments()
        );
    }

    public function testSetOrderArgumentsFewerColumns(): void
    {
        $input = [['name', 'asc']];
        $output = [['name', 'asc']];
        $this->assertEquals(
            true,
            $this->query->setOrderArguments($input)
        );
        $this->assertEquals(
            $output,
            $this->query->getOrderArguments()
        );
    }

    public function testSetOrderArgumentsTooManyColumns(): void
    {
        $input = [['name', 'asc'], ['name', 'asc'], ['name', 'asc']];
        $output = array();
        $this->assertEquals(
            false,
            $this->query->setOrderArguments($input)
        );
        $this->assertEquals(
            $output,
            $this->query->getOrderArguments()
        );
    }

    public function testGenerateSql(): void
    {
        $output = " SELECT id, name
                    FROM Religion
                    WHERE id = :id
                    ORDER BY id DESC";


        $mock = $this->getMockBuilder('Query')
             ->setMethods(array('generateSql'))
             ->getMock();


       $mock->selectArguments = [['id'], ['name']];
       $mock->whereArguments = [['id', 'eq']];
       $mock->orderArguments = [['id', 'DESC']];
       $mock->generateSql();

       $mock->expects($this->once())->method('generateSql')->with(array('selectArguments', 'whereArguments', 'orderArguments'))->willReturn(true);


        // $this->assertEquals(
        //     true,
        //     $mock->generateSql()
        // );
        $this->assertEquals(
            $output,
            $mock->sql
        );
    }
}
