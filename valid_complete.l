/* Exemplo de Programa na linguagem L */

Var char luigi [20];
Var char tanguinha = 0x5F;
Var integer salaminho [20];
Var char a = 'A';
Var integer n = 1, h, j = 4;
Var char teste [20];
Const b = 1;
Const c = +3;
Const d = -5;
Const e = 'C';

/*Programa Principal*/
teste = "Tails e Sonic";
for n = 1 to 10 step 2 do{
	if(a <> 'D') then {
		a = 'A' + 'B';
	} else{
		n = n + 1;
		;
	}
}

if((n > 1) or (n < b)) then {
	a = 'A' + 'B';
}

if((n > 1) and (n >= b)) then {
	a = 'A' + 'B';
}

if(n <= 1) then {
	a = 'A' + 'B';
}

if(n >= 1) then {
	a = 'A' + 'B';
}

for n = 1 to 10 do{
	if(n = 12) then {
		n = n % 2;
	}
}

luigi[1] = 'M';
luigi[2] = 0x41;
luigi[3] = 0x43;
luigi[4] = 0x4F;
luigi[5] = 'N';
luigi[6] = 'H';
luigi[7] = 0x41;
luigi[8] = '.';

salaminho[1] = (+5);
salaminho[2] = ((-4));
salaminho[3] = (5)+(3);
salaminho[4] = 4/2;
salaminho[5] = (+4*(-2));
salaminho[6] = -10;

if(not(a = 'D')) then {
	write(1);
	write(salaminho[1]);
	write((+5));
	writeln(1);
	writeln(salaminho[1]);
	writeln((+5));
	readln(n);
        readln(a);
}

for n = 1 to 10 do ;
/*Erro esperado: nenhum error*/
