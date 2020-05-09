<?php
declare(strict_types=1);

require_once(dirname(__FILE__, 3) . '\server\api\Recipe.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');

use PHPUnit\Framework\TestCase;
use exception\NullPointerException;

final class RecipeApiTest extends TestCase
{

    protected $recipe;

    public function setUp() : void{
        $this->recipe = new api\Recipe();
    }

    public function testErrorHandler(): void
    {
        $this->expectException(NullPointerException::class);
        $this->recipe->error_handler(1, 'Undefined index: name', 1, 1);
    }
}
