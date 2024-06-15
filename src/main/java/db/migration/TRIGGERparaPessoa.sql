CREATE OR REPLACE FUNCTION validachavepessoa() 
RETURNS trigger
    LANGUAGE plpgsql
    AS $$

  declare existe integer;

  begin 
    existe = (select count(1) from pessoa_fisica where id = NEW.pessoa_id);
    if(existe <=0 ) then 
     existe = (select count(1) from pessoa_juridica where id = NEW.pessoa_id);
    if (existe <= 0) then
      raise exception 'Não foi encontrado o ID ou PK da pessoa para realizar a associação';
     end if;
    end if;
    RETURN NEW;
  end;
  $$;
  
  create TRIGGER validaChavePessoa
  before UPDATE
  on vd_cp_loja_virt
  for each row
  execute procedure validaChavePessoa();
  
    create TRIGGER validaChavePessoa2
  before INSERT
  on vd_cp_loja_virt
  for each row
  execute procedure validaChavePessoa();
  