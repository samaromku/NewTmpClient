package com.example.andrey.newtmpclient.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.andrey.newtmpclient.entities.Address;
import com.example.andrey.newtmpclient.entities.ContactOnAddress;

public class ContactsManager {
	private List<ContactOnAddress>contactsList;
	ContactOnAddress contactOnAddress;
	AddressManager addressManager = AddressManager.INSTANCE;
	private List<ContactOnAddress>contactsOnAddress;
	private List<ContactOnAddress>uniqueContactsOnAddess;
	private List<ContactOnAddress>trueUnique = new ArrayList<>();
	Set<String>names = new HashSet<>();
	
	
	public List<ContactOnAddress> getContactsList() {
		return contactsList;
	}
	
	public List<ContactOnAddress> getContactsByAddressId(int id){
		clearUniqueContacts();
		clearTrueUnique();
		namesClear();
		for(ContactOnAddress contact:contactsList){
			if(contact.getAddressId()==id){
				addContacttoUnique(contact);
				addUniqueNames(contact);
			}
		}
		return getUniqueCOntacts();
	}
	
	public List<ContactOnAddress> getUniqueCOntacts(){
		for(String name:names){
			goOnListNames(name);
		}
		
		for(ContactOnAddress c:uniqueContactsOnAddess){
			getTrueUnique(c);
		}
		return trueUnique;
	}
	
	private void namesClear(){
		names.clear();
	}
	
	private void clearTrueUnique(){
		trueUnique.clear();
	}
	
	private void addUniqueNames(ContactOnAddress contact){
		names.add(contact.getName());
	}
	
	
	private void getTrueUnique(ContactOnAddress contact){
		for(ContactOnAddress c:trueUnique){
			if(c.getName().equals(contact.getName())){
				//добавить телефон, емейл и вернуться
				c.addPhone(contact.getPhone());
				c.addEmail(contact.getEmail());
				return;
			}
		}
	}
	
	private void goOnListNames(String name){
		for(ContactOnAddress contact:uniqueContactsOnAddess){
			if(contact.getName().equals(name)){
				trueUnique.add(contact);
				return;
			}
		}
	}
	
	private void addContacttoUnique(ContactOnAddress contact){
		uniqueContactsOnAddess.add(contact);
	}
		
	private void clearUniqueContacts(){
		if(uniqueContactsOnAddess.size()>0){
			uniqueContactsOnAddess.clear();
		}
	}
	
	public void addContact(ContactOnAddress contact){
		contactsList.add(contact);
	}

	public void removeEmptyPhonesEmails(){
        for(ContactOnAddress c:contactsList){
            c.getPhones().removeAll(Arrays.asList("", null));
            c.getEmails().removeAll(Arrays.asList("", null));
        }
    }

	public static final ContactsManager Instance = new ContactsManager();

	private ContactsManager(){
		contactsList = new ArrayList<>();
		contactsOnAddress = new ArrayList<>();
		uniqueContactsOnAddess = new ArrayList<>();
	}
	
	
	public void removeAll(){
        if(contactsList.size()>0){
        	contactsList.clear();
        }
    }
	
	public void setContactsAddress(){
		for(ContactOnAddress contact:contactsList){
			Address address = addressManager.getAddressById(contact.getAddressId());
			contact.setAddress(address.getAddress());
			contact.setOrgName(address.getName());
		}
	}
	
	
	public void insertTasksIntoDb(){
		contactsList.add(new ContactOnAddress("ТСЖ Дом академика Д. С. Лихачева", "2-й Муринский пр., д. 34, к. 1, лит. Б", "председатель", "Марина Александровна Растегаева", "89218684138", "dom34k1@mail.ru", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Дом академика Д. С. Лихачева", "2-й Муринский пр., д. 34, к. 1, лит. Б", "бухгалтер", "Татьяна Давидовна", "89119733397", "", ""));
		contactsList.add(new ContactOnAddress("ТСН Авиаконструкторов-11", "Авиаконструкторов пр., д.11, к.1", "председатель ", "Кудрявцева Вера Алексеевна", "89217465154", "kva0101@yandex.ru", ""));
		contactsList.add(new ContactOnAddress("ТСН Авиаконструкторов-11", "Авиаконструкторов пр., д.11, к.1", "председатель ", "Кудрявцева Вера Алексеевна", "6433684", "", ""));
		contactsList.add(new ContactOnAddress("ТСН Авиаконструкторов-11", "Авиаконструкторов пр., д.11, к.1", "инженер Сев. Пальмира ", "Алена", "89522331310", "", ""));
		contactsList.add(new ContactOnAddress("ТСН Авиаконструкторов-11", "Авиаконструкторов пр., д.11, к.1", "инженер Сев. Пальмира ", "Алена", "8918337450", "", ""));
		contactsList.add(new ContactOnAddress("1015 ЖСК", "Тихорецкий пр., д. 25, к.4", "председатель", "Рогатко Любовь Анатольевна", "89118166493", "rogatkola@mail.ru", ""));
		contactsList.add(new ContactOnAddress("1015 ЖСК", "Тихорецкий пр., д. 25, к.4", "председатель", "Рогатко Любовь Анатольевна", "89313426170", "", ""));
		contactsList.add(new ContactOnAddress("1015 ЖСК", "Тихорецкий пр., д. 25, к.4", "председатель", "Рогатко Любовь Анатольевна", "5560988", "", ""));
		contactsList.add(new ContactOnAddress("1015 ЖСК", "Тихорецкий пр., д. 25, к.4", "бухгалтер", "Ольга Николаевна", "", "o-nikol@list.ru", ""));
		contactsList.add(new ContactOnAddress("1015 ЖСК", "Тихорецкий пр., д. 25, к.4", "зам. председателя", "Галина Александровна", "89117547645", "", ""));
		contactsList.add(new ContactOnAddress("1015 ЖСК", "Тихорецкий пр., д. 25, к.4", "техник", "Олег Александрович", "89052728581", "", ""));
		contactsList.add(new ContactOnAddress("1054 ЖСК", "пр. Луначарского, д. 42, к. 2", "председатель", "Вячеслав Алексеевич Кравченко", "89214092246", "kruvt57@mail.ru", ""));
		contactsList.add(new ContactOnAddress("1054 ЖСК", "пр. Луначарского, д. 42, к. 2", "бухгалтер", "Любовь Ивановна", "89112889128", "", ""));
		contactsList.add(new ContactOnAddress("1054 ЖСК", "пр. Луначарского, д. 42, к. 2", "диспетчер АДС", "Любовь Ивановна", "89219630405", "", ""));
		contactsList.add(new ContactOnAddress("1054 ЖСК", "пр. Луначарского, д. 42, к. 2", "РИФ", "диспетчер", "9630405", "", ""));
		contactsList.add(new ContactOnAddress("1102 ЖСК", "ул. Ленская, д. 3, к. 2", "Председатель", "Дмитриева Елена Леонидовна", "9318987", "zsk1102@mail.ru", ""));
		contactsList.add(new ContactOnAddress("1103 ЖСК", "ул. Ленская, д. 3, к. 2", "председатель", "Валентина Павловна Мочалова", "89602778415", "valentina_pavlovna49@mail.ru", ""));
		contactsList.add(new ContactOnAddress("1103 ЖСК", "ул. Ленская, д. 3, к. 2", "председатель", "Валентина Павловна Мочалова", "4622771", "", ""));
		contactsList.add(new ContactOnAddress("1103 ЖСК", "ул. Ленская, д. 3, к. 2", "бухгалтер", "Людмила Николаевна", "89062693043", "", ""));
		contactsList.add(new ContactOnAddress("1188 ЖСК", "пр. Суздальский, д. 5, к. 1", "председатель", "Владимир Григорьевич Арцюх", "89062530953", "jsk-1188@rambler.ru", ""));
		contactsList.add(new ContactOnAddress("1205 ЖСК", "ул. Парашютная, д. 22", "Председатель", "Татьяна Васильевна", "89112340898", "", ""));
		contactsList.add(new ContactOnAddress("1208 ЖСК", "пр. Королева, д. 24", "Техник", "Валентина", "89215998852", "", ""));
		contactsList.add(new ContactOnAddress("1216 ЖСК", "пр. Королева, д. 27, к. 2", "председатель", "Парфенова  Елена Анатольевна", "89516781331", "gkh1237ip@yandex.ru", "2"));
		contactsList.add(new ContactOnAddress("1216 ЖСК", "пр. Королева, д. 27, к. 2", "председатель", "Парфенова  Елена Анатольевна", "факс3023607", "", ""));
		contactsList.add(new ContactOnAddress("1216 ЖСК", "пр. Королева, д. 27, к. 2", "зам. председателя", "Людмила Ивановна", "89117229241", "", "108"));
		contactsList.add(new ContactOnAddress("1234 ЖСК", "пр. Энгельса, д. 149, к. 2", "председатель", "Мария Федоровна", "89516653903", "mariebrend@mail.ru", ""));
		contactsList.add(new ContactOnAddress("1234 ЖСК", "пр. Энгельса, д. 149, к. 2", "председатель", "Мария Федоровна", "5174083", "", ""));
		contactsList.add(new ContactOnAddress("1235 ЖСК", "пр. Энгельса, д. 149, к. 1", "председатель", "Елена Петровна Демьяненко", "89214044418", "leartis@leartis.ru", ""));
		contactsList.add(new ContactOnAddress("1302 ЖСК", "ул. Шаврова, д. 17", "председатель", "Надежда Николаевна", "89219582717", "nade-spb@mail.ru", "163"));
		contactsList.add(new ContactOnAddress("1307 ЖСК", "Богатырский пр.,д. 37, к. 2", "домоуправ", "Валентина Михайловна", "89052873697", "evfarafonova@gmail.com", ""));
		contactsList.add(new ContactOnAddress("1307 ЖСК", "Богатырский пр.,д. 37, к. 2", "председатель", "Владимир Андреевич", "89112283052", "gsk-1307@mail.ru", ""));
		contactsList.add(new ContactOnAddress("1307 ЖСК", "Богатырский пр.,д. 37, к. 2", "председатель", "Владимир Андреевич", "3484834", "", ""));
		contactsList.add(new ContactOnAddress("1333 ЖСК", "ул. Мартыновская, д. 10, к. 1", "председатель ", "Чуйко П. М.", "9470909", "jsk1333@mail.ru", ""));
		contactsList.add(new ContactOnAddress("1333 ЖСК", "ул. Мартыновская, д. 10, к. 1", "бухгалтер ", "Лера Николаевна", "3490529", "rossina1938@mail.ru", ""));
		contactsList.add(new ContactOnAddress("1333 ЖСК", "ул. Мартыновская, д. 10, к. 1", "мастер", "Дмитрий Иванов", "89110927331", "", ""));
		contactsList.add(new ContactOnAddress("1333 ЖСК", "ул. Мартыновская, д. 10, к. 1", "мастер", "Дмитрий Иванов", "5732226", "", ""));
		contactsList.add(new ContactOnAddress("224 ЖСК", "Ланское шоссе, д. 33, к. 2", "председатель", "Васильева М.В.", "79119091811", "mv-vas@yandex.ru", ""));
		contactsList.add(new ContactOnAddress("224 ЖСК", "Ланское шоссе, д. 33, к. 2", "председатель", "Васильева М.В.", "4929945", "", ""));
		contactsList.add(new ContactOnAddress("296 ЖСК", "Науки пр., д. 67", "председатель", "Любовь Васильевна", "89219183406", "hbs296@yandex.ru", ""));
		contactsList.add(new ContactOnAddress("300 ЖСК", "ул. Гаврская, д. 6", "Председатель", "Вера Михайловна", "89219713888", "verkor59@gmail.com", "10"));
		contactsList.add(new ContactOnAddress("300 ЖСК", "ул. Гаврская, д. 6", "Председатель", "Вера Михайловна", "2936893", "", ""));
		contactsList.add(new ContactOnAddress("300 ЖСК", "ул. Гаврская, д. 6", "Обслуж. Компания", "Смирнов Андрей ", "89626978888", "", ""));
		contactsList.add(new ContactOnAddress("349 ЖСК", "Ланское шоссе, д. 26", "председатель", "Глушко   Ирина Олеговна", "89119477213", "gsk349@gmail.com", "40"));
		contactsList.add(new ContactOnAddress("349 ЖСК", "Ланское шоссе, д. 26", "председатель", "Глушко   Ирина Олеговна", "4923761", "", ""));
		contactsList.add(new ContactOnAddress("349 ЖСК", "Ланское шоссе, д. 26", "бухгалтер", "Васильева Евгения Дмитриевна", "2471336", "", ""));
		contactsList.add(new ContactOnAddress("349 ЖСК", "Ланское шоссе, д. 26", "МАСТЕР, охта сервис", "Валентина", "89112203728", "", ""));
		contactsList.add(new ContactOnAddress("37 ЖСК", "Гражданский пр., д. 9, к. 1", "председатель", "Головина Наталья Васильевна", "9036085", "golovin.nat@yandex.ru", "43"));
		contactsList.add(new ContactOnAddress("37 ЖСК", "Гражданский пр., д. 9, к. 1", "бухгалтер", "Елена Юрьевна", "89117993330", "zolotareva_@mail.ru", ""));
		contactsList.add(new ContactOnAddress("40 ЖК", "ул. Камышовая, д. 3, к. 1", "зам. председателя", "Кибиткин Валерий Иванович", "89062273855", "", "153"));
		contactsList.add(new ContactOnAddress("40 ЖК", "ул. Камышовая, д. 3, к. 1", "зам. председателя", "Кибиткин Валерий Иванович", "3430285", "", ""));
		contactsList.add(new ContactOnAddress("40 ЖК", "ул. Камышовая, д. 3, к. 1", "председатель", "Галактионова Ирина Анатольевна", "89119654110", "gkh40@yandex.ru", ""));
		contactsList.add(new ContactOnAddress("40 ЖК", "ул. Камышовая, д. 3, к. 1", "председатель", "Галактионова Ирина Анатольевна", "3484105", "", ""));
		contactsList.add(new ContactOnAddress("40 ЖК", "ул. Камышовая, д. 3, к. 1", "Обс. организация", "", "3426796", "", ""));
		contactsList.add(new ContactOnAddress("416 ЖСК", "Гражданский пр., д.71, к.1", "председатель", "Аничкина Татьяна Георгиевна", "89052139950", "anichkinat@rambler.ru", ""));
		contactsList.add(new ContactOnAddress("416 ЖСК", "Гражданский пр., д.71, к.1", "председатель", "Аничкина Татьяна Георгиевна", "5339347", "", ""));
		contactsList.add(new ContactOnAddress("529 ЖСК", "ул.Брянцева, д. 28", "Председатель", "Лукина Зинаида Владимировна ", "89500405133", "pravlenie529@ya.ru", "91 кв"));
		contactsList.add(new ContactOnAddress("529 ЖСК", "ул.Брянцева, д. 28", "Председатель", "Лукина Зинаида Владимировна ", "5577515", "", ""));
		contactsList.add(new ContactOnAddress("637 ЖСК", "ул. Ушинского, д. 41", "Председатель", "Наталья Прощенко", "89062460755", "nataxe@yandex.ru", ""));
		contactsList.add(new ContactOnAddress("763 ЖСК", "ул. Ушинского, д. 23, к. 2", "председатель", "Татьяна Борисовна", "89213206833", "tatynapantac@mail.ru ", ""));
		contactsList.add(new ContactOnAddress("763 ЖСК", "ул. Ушинского, д. 23, к. 2", "председатель", "Татьяна Борисовна", "5900292", "", ""));
		contactsList.add(new ContactOnAddress("763 ЖСК", "ул. Ушинского, д. 23, к. 2", "бухгалтер", "Галина Ивановна", "89217531663", "galina shafar@mail.ru", ""));
		contactsList.add(new ContactOnAddress("764 ЖСК", "ул. Ушинского, д. 19, к. 1", "председатель", "Панчак Павел Петрович", "89217504733", "tatynapantac@mail.ru ", ""));
		contactsList.add(new ContactOnAddress("764 ЖСК", "ул. Ушинского, д. 19, к. 1", "бухгалтер", "Галина Ивановна", "89217531663", "galina shafar@mail.ru", ""));
		contactsList.add(new ContactOnAddress("764 ЖСК", "ул. Ушинского, д. 19, к. 1", "зам. председателя", "Вера Евгеньевна", "89522853389", "", ""));
		contactsList.add(new ContactOnAddress("766 ЖСК", "ул. Ушинского, д. 11", "председатель", "Анна Валерьевна", "89046066056", "", ""));
		contactsList.add(new ContactOnAddress("829 ЖСК", "ул. Демьяна Бедного, д. 23, к. 2", "председатель ", "Алексеев Юрий Александрович", "89217508742", "gsk829@mail.ru", ""));
		contactsList.add(new ContactOnAddress("829 ЖСК", "ул. Демьяна Бедного, д. 23, к. 2", "бухгалтер", "Татьяна Геннадьевна", "89219287684", "", ""));
		contactsList.add(new ContactOnAddress("829 ЖСК", "ул. Демьяна Бедного, д. 23, к. 2", "бухгалтер", "Татьяна Геннадьевна", "4306842", "", ""));
		contactsList.add(new ContactOnAddress("854 ЖСК", "ул. Брянцева, д. 6", "", "", "", "", ""));
		contactsList.add(new ContactOnAddress("931 ЖСК", "Светлановский пр. д. 48/19", "председатель ", "Наталья Константиновна", "89215718621", "natalya.syulgina@mail.ru", "2 пар. Код 70"));
		contactsList.add(new ContactOnAddress("931 ЖСК", "Светлановский пр. д. 48/19", "председатель ", "Наталья Константиновна", "6959655", "", ""));
		contactsList.add(new ContactOnAddress("931 ЖСК", "Светлановский пр. д. 48/19", "Управляющая ", "Валентина Петровна", "89219456337", "", ""));
		contactsList.add(new ContactOnAddress("БАМО, ООО", "Обуховской обороны пр., д.89а", "", "Зырянов Александр Владимирович", "89119275003", "of-spb@mail.ru", ""));
		contactsList.add(new ContactOnAddress("Блиц, ООО", "пос.Ново-Девяткино, ул. Школьная, д. 15а", "", "Новиков Андрей", "89112216763", "novikov@oknaweka.ru", ""));
		contactsList.add(new ContactOnAddress("Блиц, ООО", "пос.Ново-Девяткино, ул. Школьная, д. 15а", "", "Новиков Андрей", "89218560731", "", ""));
		contactsList.add(new ContactOnAddress("Венец, ООО", "пр. Энгельса, д. 109", "", "Виктор Сергеевич", "9279211", "", ""));
		contactsList.add(new ContactOnAddress("Венец, ООО", "пр. Энгельса, д. 109", "", "Виктор Сергеевич", "факс5918600", "", ""));
		contactsList.add(new ContactOnAddress("ВИРИАЛ, ООО", "пр. Энгельса, д. 27", "зам глав. Энергетика", "Романов Роман Юрьевич", "89217505784", "romanovrj@virial.ru", ""));
		contactsList.add(new ContactOnAddress("ВИРИАЛ, ООО", "пр. Энгельса, д. 27", "зам глав. Энергетика", "Романов Роман Юрьевич", "7021068", "", ""));
		contactsList.add(new ContactOnAddress("Гейзерком, ООО", "ш. Революции, д. 69", "", "", "", "hv@geizer.com", ""));
		contactsList.add(new ContactOnAddress("ГМПИР Болотная", "ул.Болотная, д. 13А", "Зам. Директора", "Гришина Валентина Алексеевна", "89310019904", "muzei.xozchast@yandex.ru", ""));
		contactsList.add(new ContactOnAddress("ГМПИР Болотная", "ул.Болотная, д. 13Б", "Зам. Директора", "Гришина Валентина Алексеевна", "2337048", "muzei.xozchast@yandex.ru", ""));
		contactsList.add(new ContactOnAddress("ЖСК Им.ХХ Съезда", "ул. Гданьская, д. 11", "председатель", "Татьяна Александровна", "89312505368", "tat.puzh@yandex.ru", "61"));
		contactsList.add(new ContactOnAddress("ЖСК Им.ХХ Съезда", "ул. Гданьская, д. 11", "председатель", "Татьяна Александровна", "5542908", "", ""));
		contactsList.add(new ContactOnAddress("ЖСК Приневский  ", "Октябрьская наб., д.124, к.5", "диспетчер возле уутэ", "", "4411930", "", ""));
		contactsList.add(new ContactOnAddress("ЖСК Приневский  ", "Октябрьская наб., д.124, к.5", "диспетчер возле уутэ", "", "факс4411930", "", ""));
		contactsList.add(new ContactOnAddress("ЖСК Приневский  ", "Октябрьская наб., д.124, к.5", "председатель", "Луценко Любовь Сергеевна", "89112821102", "prinevski@mail.ru", ""));
		contactsList.add(new ContactOnAddress("Зеленогорская типография", "г. Зеленогорск , пр. Ленина, д. 44, Лит А, Б.", "", "Евгения Ивановна", "89219576263", "zeltip@bk.ru", ""));
		contactsList.add(new ContactOnAddress("Зеленогорская типография", "г. Зеленогорск , пр. Ленина, д. 44, Лит А, Б.", "", "Евгения Ивановна", "факс4333927", "", ""));
		contactsList.add(new ContactOnAddress("Лабиринт, ООО", "ул.Есенина, д.18, литер А, корп.2", "генеральный директор", "Татьяна Георгиевна", "89219574977", "t.kisteneva@gmail.com", ""));
		contactsList.add(new ContactOnAddress("ЛенНИИпроект", "Афонская  ул., д. 2", "", "Жанна Владимировна", "89214067012", "smetaninaz@mail.ru", ""));
		contactsList.add(new ContactOnAddress("ЛенНИИпроект", "Афонская  ул., д. 2", "деж. электрик", "Игорь Николаевич", "3020340", "", ""));
		contactsList.add(new ContactOnAddress("ЛенНИИпроект", "Афонская  ул., д. 2", "", "Иванов Владимир", "89216381581", "", ""));
		contactsList.add(new ContactOnAddress("ЛенНИИпроект", "Афонская  ул., д. 2", "Гл. инженер", "Яремчук Анатолий Петрович", "89217518621", "", ""));
		contactsList.add(new ContactOnAddress("ЛенНИИпроект", "Афонская  ул., д. 2", "Электрик", "Терлецкий Игорь Николаевич", "89095771683", "", ""));
		contactsList.add(new ContactOnAddress("Налоговая Лен. Обл.", "пр. Металлистов, д. 34", "Помощник нач.хоз. отдела", "Железняк Валерий Аркадьевич", "89118202444", "u471503@r47.nalog.ru", ""));
		contactsList.add(new ContactOnAddress("Омега, АО", "ул.Тамбовская, д.46-б", "Директор", "Ульянов Илья Александрович", "89219488287", "3783061@mail.ru", ""));
		contactsList.add(new ContactOnAddress("Омега, АО", "ул.Тамбовская, д.46-б", "бухгалтерия", "Елена Дмитриевна", "89112529379", "", ""));
		contactsList.add(new ContactOnAddress("Омега, АО", "ул.Тамбовская, д.46-б", "бухгалтерия", "Елена Дмитриевна", "факс7662257", "", ""));
		contactsList.add(new ContactOnAddress("Омега, АО", "ул.Тамбовская, д.46-б", "гл. энергетик", "Cергей Васильевич", "89312717747", "", ""));
		contactsList.add(new ContactOnAddress("ПСО", "п. Песочный, ул. Речная, д. 49", "", "", "", "", ""));
		contactsList.add(new ContactOnAddress("Сапфир, ООО", "пр. Тореза, д.102, к.4", "", "", "", "", ""));
		contactsList.add(new ContactOnAddress("Свидетели Иговы", "ул. Съезжинская, д. 7, ул. Зверинская, д. 10", "", "Бочаров Андрей", "89112293611", "andlen2005@yandex.ru", ""));
		contactsList.add(new ContactOnAddress("Смостур, ООО", "Комендатский пр., д. 32, к. 5", "отв. ", "Александр Георгиевич", "89062772032", "", ""));
		contactsList.add(new ContactOnAddress("Смостур, ООО", "Комендатский пр., д. 32, к. 5", "отв. ", "Александр Георгиевич", "3481313", "", ""));
		contactsList.add(new ContactOnAddress("Смостур, ООО", "Комендатский пр., д. 32, к. 5", "", "", "факс.3074858", "", ""));
		contactsList.add(new ContactOnAddress("Смостур, ООО", "Комендатский пр., д. 32, к. 5", "гл инженер", "Мила Зак", "", "zack.mila@yandex.ru", ""));
		contactsList.add(new ContactOnAddress("Смостур, ООО", "Комендатский пр., д. 32, к. 5", "", "Борис Альтерман", "89219678148", "", ""));
		contactsList.add(new ContactOnAddress("Смостур, ООО", "Комендатский пр., д. 32, к. 5", "", "Илья", "89990346356", "", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "Гражданский пр., д. 28", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-57", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "Гражданский пр., д. 30", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-58", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "Малодетскосельский пр., д. 27", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-59", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "Полюстровский пр., д. 14, лит. А", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-60", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "Полюстровский пр., д. 14, лит. Б", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-61", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "пр. Непокоренных, д. 6, к.2", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-62", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "пр. Непокоренных, д. 8, к.2", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-63", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "Приморское ш., д. 63", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-64", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Вавиловых, д. 10, к. 2", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-65", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Вавиловых, д. 10, к. 3", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-66", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Гастелло, д. 19", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-67", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Гжатская, д. 27", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-68", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Жукова, д. 13, лит. А", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-69", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Жукова, д. 17, лит. А", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-70", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Новороссийская, д. 48", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-71", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Новороссийская, д. 50", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-72", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Обручевых, д. 5", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-73", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Обручевых, д. 5", "", "Бурганов Руслан Тэлгэтович", "8 921 315 44 78", "", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Политехническая, д. 29 (1-й проф.)", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-74", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Политехническая, д. 29 (2-й проф.)", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-75", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Политехническая, д. 29 (гараж)", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-76", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Политехническая, д. 31", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-77", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Хлопина, д. 11", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-78", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Хлопина, д. 13", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-79", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Хлопина, д. 3", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-80", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Хлопина, д. 3(внутренний)", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-81", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Хлопина, д. 3(основной)", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-82", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Хлопина, д. 5", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-83", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Хлопина, д. 7, к.1", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-84", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Хлопина, д. 7, к.2", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-85", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Хлопина, д. 7, к.3", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-86", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("СПбГПУ", "ул. Чехова, д. 6", "", "Иванов Дмитрий Геннадьевич", "8-921-561-05-87", "mitrich.71@inbox.ru", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Академика Константинова д.16", "ул Академика Константинова, д. 16", "Председатель", "Дмитрий/Денис", "89095805114", "", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Академическое", "Гражданский пр., д.79, к.4", "председатель", "Костюкова Светлана Анатольевна", "8951-651-83-17", "<s_kostykova@mail.ru>", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Академическое", "Гражданский пр., д.79, к.4", "Диспетчер", "", "590-88-34", "", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Академическое", "Гражданский пр., д.79, к.4", "зам. председателя", "Тамара  Павловна ", "8 911 172 40 64", "", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Академическое", "Гражданский пр., д.79, к.4", "Зам предс", "Сергей Константинович", "8 911 707 62 56", "", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Богатырский 59", "Богатырский пр., д. 59", "председатель", "Никифорова Ольга Ивановна", "8 904 642 29 46", "bogat59@mail.ru", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Богатырский 59", "Богатырский пр., д. 60", "председатель", "Никифорова Ольга Ивановна", "8-911-739-10-12", "", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Богатырский 59", "Богатырский пр., д. 61", "председатель", "Никифорова Ольга Ивановна", "342-57-65", "", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Георгиевское", "Новоколомяжский пр., д 15", "Председатель", "Медведев Вячеслав Зейналович", "", "medvedevvl@mail.ru", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Георгиевское", "Новоколомяжский пр., д 21", "Председатель", "Медведев Вячеслав Зейналович", "", "medvedevvl@mail.ru", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Елагинский", "пр.Елагинский, д.40, к.4", "председатель", "Лисицына Татьяна Александровна", "8 921 334 55 68", "tat.lisiczina2014@yandex.ru", "137"));
		contactsList.add(new ContactOnAddress("ТСЖ Елагинский", "пр.Елагинский, д.40, к.4", "председатель", "Лисицына Татьяна Александровна", "341-05-84", "", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Елагинский", "пр.Елагинский, д.40, к.4", "", "Татьяна Дмитриевна", "89117167940", "", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Комендант", "пр. Сизова, д. 22", "Председатель", "Юферов Борис Валентинович", "89095805114", "", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Кондоминимум 48", "ул. Камышовая, д. 5", "председатель", "Мельниченко Елена Борисовна", "8 952 203 92 10", "kondominium48@gmail.com", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Кондоминимум 48", "ул. Камышовая, д. 5", "председатель", "Мельниченко Елена Борисовна", "343-06-15  ", "", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Наш Дом", "ул. Ильюшина, д. 13 к. 2, лит.А", "Председатель", "Наталья Борисовна Пяткова", "8 921 637 44 10", "pyatkov_ivan@mail.ru", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Наш Дом", "ул. Ильюшина, д. 13 к. 2, лит.А", "Председатель", "Наталья Борисовна Пяткова", "факс.342-68-84", "", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Наш Дом", "ул. Ильюшина, д. 13 к. 2, лит.А", "обс. Комп", "Андриянова Ольга", "3392655", "", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Наш Дом", "ул. Ильюшина, д. 13 к. 2, лит.А", "обс. Комп", "Андриянова Ольга", "89217599513", "", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Наш Дом", "ул. Ильюшина, д. 13 к. 2, лит.А", "обс. Комп", "Андриянова Ольга", "9204025", "", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Наш Дом", "ул. Ильюшина, д. 13 к. 2, лит.А", "", "Надежда Владимировна", "8 921 186 52 41", "", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Политехническая-17", "ул. Политехническая, д. 17, к.1", "председатель", "Виталий Леонидович Пеньков", "8-904-639-53-84", "vit.penkoff@mail.ru", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Политехническая-17", "ул. Политехническая, д. 17, к.1", "зам. председателя", "Лилия Анисимовна", "915-47-82", "", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Просвет", "пр.Просвещения, д. 68, к.1", "председатель", "Грибанова Светлана Владимировна", "903-00-12", "", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Просвет", "пр.Просвещения, д. 68, к.1", "мастер", "Любовь Егоровна", "89627271846", "pr@prosvet68.ru", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Светлановский 79", "Светлановский пр., д. 79", "председатель ", "Александр Александрович Гусев", "8 960 240 35 67", "tsg_svet_79@mail.ru, ", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Светлановский 79", "Светлановский пр., д. 79", "зам. председателя", "Андронова Лариса Дмитриевна", "", "lorchends@mail.ru", ""));
		contactsList.add(new ContactOnAddress("ТСЖ СПб, Светлановский просп., д. 81/21", "Светлановский пр., д. 81/21, лит.А", "Председатель", "Фаина Филлиповна", "89215906279", "", ""));
		contactsList.add(new ContactOnAddress("ТСЖ Эрмитаж-1", "Тихорецкий пр.,  д. 10, к. 2", "председатель", "Ильяш Вероника Александровна", "8 950 037 12 96", " veronika-1937@mail.ru", "17"));
		contactsList.add(new ContactOnAddress("ТСЖ Эрмитаж-1", "Тихорецкий пр.,  д. 10, к. 2", "председатель", "Ильяш Вероника Александровна", "297 82 08", "", ""));
		contactsList.add(new ContactOnAddress("ТСН Благо", "ул. Планерная, д. 47, к. 4", "председатель ", "Александра Андреевна Воротникова", "8-921-308-41-99", "spbblago@mail.ru", ""));
		contactsList.add(new ContactOnAddress("ТСН Планер", "ул. Планерная, д. 47, к. 5", "председатель", "Гарусова  Ирина ", "8-931-265-47-04", "planer2017@mail.ru", ""));

	}
}
