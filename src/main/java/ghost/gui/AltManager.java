package ghost.gui;

import java.util.ArrayList;
import java.util.List;

import ghost.Client;
import ghost.utils.Alt;
import ghost.utils.notifications.NotifType;
import ghost.utils.notifications.NotifUtils;
import net.lax1dude.eaglercraft.ConfigConstants;
import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.EaglerProfile;
import net.lax1dude.eaglercraft.EaglercraftRandom;
import net.lax1dude.eaglercraft.LocalStorageManager;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.MathHelper;

public class AltManager extends GuiScreen {
	
	private GuiTextField username;
	private String user = "";
	private GuiTextField password;
	private String pass = "";
	
	private GuiTextField genAlts;
	private String altgen = "";
	
	public List<Alt> alts = new ArrayList<Alt>();
	
	private int offset = 0;
	
	@Override
	public void updateScreen() {
		this.username.updateCursorCounter();
		this.password.updateCursorCounter();
		this.genAlts.updateCursorCounter();
		super.updateScreen();
	}
	
	@Override
	public void onGuiClosed() {
		mc.gameSettings.saveOptions();
		super.onGuiClosed();
	}
	
	@Override
	protected void keyTyped(char par1, int par2) {
		this.username.textboxKeyTyped(par1, par2);
		this.password.textboxKeyTyped(par1, par2);
		this.pass = this.password.getText();
		this.genAlts.textboxKeyTyped(par1, par2);
		String text = username.getText();
		if(text.length() > 16) text = text.substring(0, 16);
		text = text.replaceAll("[^A-Za-z0-9\\-_]", "_");
		this.username.setText(text);
		user = text;
		text = this.genAlts.getText();
		text = text.replaceAll("[^0-9]+", "");
		altgen = text;
		this.genAlts.setText(text);
		super.keyTyped(par1, par2);
	}
	
	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		this.username.mouseClicked(par1, par2, par3);
		this.genAlts.mouseClicked(par1, par2, par3);
		this.password.mouseClicked(par1, par2, par3);
		super.mouseClicked(par1, par2, par3);
	}
	
	@Override
	public void handleMouseInput() {
		if(getListMaxScroll()+this.height >= this.height) {
			int wheel = EaglerAdapter.mouseGetEventDWheel();
				if (wheel < 0) {
					new Thread() {
						@Override
						public void run() {
							for(int i = 0; i < 30; i++) {
								offset = MathHelper.clamp_int(offset+1, 0, getListMaxScroll());
								try {
									sleep(5);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}.start();
				} else if (wheel > 0) {
					new Thread() {
						@Override
						public void run() {
							for(int i = 0; i < 30; i++) {
								offset = MathHelper.clamp_int(offset-1, 0, getListMaxScroll());
								try {
									sleep(5);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}.start();
				}
		}
		super.handleMouseInput();
	}
	
	private int getListMaxScroll() {
		return alts.size()*40+10-this.height;
	}
	
	private boolean debounceClick = false;
	private boolean debounceClickDelete = false;

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.drawDefaultBackground();
		fontRenderer.drawString("Currently §aconnected§r as: §b" + EaglerProfile.username, 10, 10, -1);
		fontRenderer.drawString("Alts to gen: ", 10, 90,-1);
		fontRenderer.drawString("Total Alts: "+String.valueOf(alts.size()), 10, 160,-1);
		this.username.drawTextBox();
		this.genAlts.drawTextBox();
		this.password.drawTextBox();
		int alt = 0;
		int altToRemove = -1;
		for(Alt a : alts) {
			if(10+40*alt-offset+3 > this.height) {
				break;
			}
			if(!debounceClick && EaglerAdapter.mouseIsButtonDown(0) && (par1 >= 216 && par2 >= 10+40*alt-offset-2 && par1 <= 216+190 && par2 <= 10+40*alt-offset+30) && !(par1 >= 216+180 && par2 >= 10+40*alt-offset+20 && par1 <= 216+180+fontRenderer.getStringWidth("X") && par2 <= 10+40*alt-offset+20+fontRenderer.FONT_HEIGHT)) {
				EaglerProfile.username = a.username;
				LocalStorageManager.profileSettingsStorage.setString("name", EaglerProfile.username);
				debounceClick = true;
			} else if(debounceClick && !EaglerAdapter.mouseIsButtonDown(0)) {
				debounceClick = false;
			}
			if(!debounceClickDelete && EaglerAdapter.mouseIsButtonDown(0) && (par1 >= 216+180 && par2 >= 10+40*alt-offset+20 && par1 <= 216+180+fontRenderer.getStringWidth("X") && par2 <= 10+40*alt-offset+20+fontRenderer.FONT_HEIGHT)) {
				altToRemove = alt;
				debounceClickDelete = true;
			} else if(debounceClickDelete && !EaglerAdapter.mouseIsButtonDown(0)) {
				debounceClickDelete = false;
			}
			Gui.drawRect(216, 10+40*alt-offset-2, 216+190, 10+40*alt-offset+30, 0xff000000);
			fontRenderer.drawString("X",216+180,10+40*alt-offset+20,0xffff0000);
			fontRenderer.drawString(a.username, 216+190/2 - fontRenderer.getStringWidth(a.username)/2, 10+40*alt-offset+3, -1);
			fontRenderer.drawString("State: " + (EaglerProfile.username == a.username ? "§2Connected" : "§cDisconnected"), 216+190/2 - fontRenderer.getStringWidth("State: " + (EaglerProfile.username == a.username ? "Connected" : "Disconnected"))/2, 10+40*alt-offset+3+fontRenderer.FONT_HEIGHT, -1);
			alt++;
		}
		if(altToRemove != -1) {
			alts.remove(altToRemove);
		}
		NotifUtils.renderNotifications();
		super.drawScreen(par1, par2, par3);
	}
	
	@Override
	public void initGui() {
		this.username = new GuiTextField(this.fontRenderer, 10, 30, 100, 20);
		this.genAlts = new GuiTextField(this.fontRenderer, 10, 103, 200, 20);
		this.password = new GuiTextField(this.fontRenderer, 110,30,100,20,true);
		this.buttonList.add(new GuiButton(3, 8,103+30,205,20,"Generate alts"));
		this.buttonList.add(new GuiButton(1, 8,60,205,20,"Add Account"));
		this.buttonList.add(new GuiButton(2, 8,this.height-30,205,20,"Back"));
		this.buttonList.add(new GuiButton(4, 8,this.height-30-30,205,20,"Select Random"));
		this.username.setText(user);
		this.genAlts.setText(altgen);
		super.initGui();
	}

	public void genAlt(int number) {
		try {
			for(int i = 0; i < number; i++) {
				String[] usernameDefaultWords = new String[] {
						"aa","aaa","aah","aahed","aahing","aahs","aal","aalii","aaliis","aals","aam","aardvark","aardvarks","aardwolf","aardwolves","aargh","aaron","aaronic","aarrgh","aarrghh","aas","aasvogel","aasvogels","ab","aba","abac","abaca","abacas","abacate","abacaxi","abacay","abaci","abacinate","abacination","abacisci","abaciscus","abacist","aback","abacli","abacot","abacterial","abactinal","abactinally","abaction","abactor","abaculi","abaculus","abacus","abacuses","abada","abaddon","abadejo","abadengo","abadia","abaff","abaft","abaisance","abaised","abaiser","abaisse","abaissed","abaka","abakas","abalation","abalienate","abalienated","abalienating","abalienation","abalone","abalones","abamp","abampere","abamperes","abamps","aband","abandon","abandonable","abandoned","abandonedly","abandonee","abandoner","abandoners","abandoning","abandonment","abandonments","abandons","abandum","abanet","abanga","abannition","abapical","abaptiston","abaptistum","abarthrosis","abarticular","abarticulation","abas","abase","abased","abasedly","abasedness","abasement","abasements","abaser","abasers","abases","abash","abashed","abashedly","abashedness","abashes","abashing","abashless","abashlessly","abashment","abashments","abasia","abasias","abasic","abasing","abasio","abask","abassi","abastard","abastardize","abastral","abatable","abatage","abate","abated","abatement","abatements","abater","abaters","abates","abatic","abating","abatis","abatised","abatises","abatjour","abatjours","abaton","abator","abators","abattage","abattis","abattised","abattises","abattoir","abattoirs","abattu","abattue","abature","abaue","abave","abaxial","abaxile","abay","abayah","abaze","abb","abba","abbacies","abbacomes","abbacy","abbandono","abbas","abbasi","abbasid","abbassi","abbate","abbatial","abbatical","abbatie","abbaye","abbe","abbes","abbess","abbesses","abbest","abbevillian","abbey","abbeys","abbeystead","abbeystede","abboccato","abbogada","abbot","abbotcies","abbotcy","abbotnullius","abbotric","abbots","abbotship","abbotships","abbott","abbozzo","abbr","abbrev","abbreviatable","abbreviate","abbreviated","abbreviately","abbreviates","abbreviating","abbreviation","abbreviations","abbreviator","abbreviators","abbreviatory","abbreviature","abbroachment","abby","abc","abcess","abcissa","abcoulomb","abd","abdal","abdali","abdaria","abdat","abdest","abdicable","abdicant","abdicate","abdicated","abdicates","abdicating","abdication","abdications","abdicative","abdicator","abdicators","abditive","abditory","abdom","abdomen","abdomens","abdomina","abdominal","abdominales","abdominalia","abdominalian","abdominally","abdominals","abdominocardiac","abdominocystic","abdominogenital","abdominoscope","abdominoscopy","abdominous","abdominovaginal","abdominovesical","abduce","abduced","abducens","abducent","abducentes","abduces","abducing","abduct","abducted","abducting","abduction","abductions","abductor","abductores","abductors","abducts","abeam","abear","abearance","abecedaire","abecedaria","abecedarian","abecedarians","abecedaries","abecedarium","abecedarius","abecedary","abed","abede","abedge","abegge","abeigh","abel","abele","abeles","abelia","abelian","abelias","abelite","abelmosk","abelmosks","abelmusk","abeltree","abend","abends","abenteric","abepithymia","aberdavine","aberdeen","aberdevine","aberduvine","abernethy","aberr","aberrance","aberrances","aberrancies","aberrancy","aberrant","aberrantly","aberrants","aberrate","aberrated","aberrating","aberration","aberrational","aberrations","aberrative","aberrator","aberrometer","aberroscope","aberuncate","aberuncator","abesse","abessive","abet","abetment","abetments","abets","abettal","abettals","abetted","abetter","abetters","abetting","abettor","abettors","abevacuation","abey","abeyance","abeyances","abeyancies","abeyancy","abeyant","abfarad","abfarads","abhenries","abhenry","abhenrys","abhinaya","abhiseka","abhominable","abhor","abhorred","abhorrence","abhorrences","abhorrency","abhorrent","abhorrently","abhorrer","abhorrers","abhorrible","abhorring","abhors","abib","abichite","abidal","abidance","abidances","abidden","abide","abided","abider","abiders","abides","abidi","abiding","abidingly","abidingness","abiegh","abience","abient","abietate","abietene","abietic","abietin","abietineous","abietinic","abietite","abigail","abigails","abigailship","abigeat","abigei","abigeus","abilao","abilene","abiliment","abilitable","abilities","ability","abilla","abilo","abime","abintestate","abiogeneses","abiogenesis","abiogenesist","abiogenetic","abiogenetical","abiogenetically","abiogenic","abiogenically","abiogenist","abiogenists","abiogenous","abiogeny","abiological","abiologically","abiology","abioses","abiosis","abiotic","abiotical","abiotically","abiotrophic","abiotrophy","abir","abirritant","abirritate","abirritated","abirritating","abirritation","abirritative","abiston","abit","abiuret","abject","abjectedness","abjection","abjections","abjective","abjectly","abjectness","abjectnesses","abjoint","abjudge","abjudged","abjudging","abjudicate","abjudicated","abjudicating","abjudication","abjudicator","abjugate","abjunct","abjunction","abjunctive","abjuration","abjurations","abjuratory","abjure","abjured","abjurement","abjurer","abjurers","abjures","abjuring","abkar","abkari","abkary","abl","ablach","ablactate","ablactated","ablactating","ablactation","ablaqueate","ablare","ablastemic","ablastin","ablastous","ablate","ablated","ablates","ablating","ablation","ablations","ablatitious","ablatival","ablative","ablatively","ablatives","ablator","ablaut","ablauts","ablaze","able","ableeze","ablegate","ablegates","ablegation","ablend","ableness","ablepharia","ablepharon","ablepharous","ablepsia","ablepsy","ableptical","ableptically","abler","ables","ablesse","ablest","ablet","ablewhackets","ablings","ablins","ablock","abloom","ablow","ablude","abluent","abluents","ablush","ablute","abluted","ablution","ablutionary","ablutions","abluvion","ably","abmho","abmhos","abmodalities","abmodality","abn","abnegate","abnegated","abnegates","abnegating","abnegation","abnegations","abnegative","abnegator","abnegators","abner","abnerval","abnet","abneural","abnormal","abnormalcies","abnormalcy","abnormalise","abnormalised","abnormalising","abnormalism","abnormalist","abnormalities","abnormality","abnormalize","abnormalized","abnormalizing","abnormally","abnormalness","abnormals","abnormities","abnormity","abnormous","abnumerable","abo","aboard","aboardage","abococket","abodah","abode","aboded","abodement","abodes","aboding","abody","abogado","abogados","abohm","abohms","aboideau","aboideaus","aboideaux","aboil","aboiteau","aboiteaus","aboiteaux","abolete","abolish","abolishable","abolished","abolisher","abolishers","abolishes","abolishing","abolishment","abolishments","abolition","abolitionary","abolitionise","abolitionised","abolitionising","abolitionism","abolitionisms","abolitionist","abolitionists","abolitionize","abolitionized","abolitionizing","abolitions","abolla","abollae","aboma","abomas","abomasa","abomasal","abomasi","abomasum","abomasus","abomasusi","abominability","abominable","abominableness","abominably","abominate","abominated","abominates","abominating","abomination","abominations","abominator","abominators","abomine","abondance","abonne","abonnement","aboon","aborad","aboral","aborally","abord","aboriginal","aboriginality","aboriginally","aboriginals","aboriginary","aborigine","aborigines","aborning","aborsement","aborsive","abort","aborted","aborter","aborters","aborticide","abortient","abortifacient","abortifacients","abortin","aborting","abortion","abortional","abortionist","abortionists","abortions","abortive","abortively","abortiveness","abortivenesses","abortogenic","aborts","abortus","abortuses","abos","abote","abouchement","aboudikro","abought","aboulia","aboulias","aboulic","abound","abounded","abounder","abounding","aboundingly","abounds","about","abouts","above","aboveboard","abovedeck","aboveground","abovementioned","aboveproof","aboves","abovesaid","abovestairs","abow","abox","abp","abr","abracadabra","abracadabras","abrachia","abrachias","abradable","abradant","abradants","abrade","abraded","abrader","abraders","abrades","abrading","abraham","abraid","abranchial","abranchialism","abranchian","abranchiate","abranchious","abrasax","abrase","abrased","abraser","abrash","abrasing","abrasiometer","abrasion","abrasions","abrasive","abrasively","abrasiveness","abrasivenesses","abrasives","abrastol","abraum","abraxas","abray","abrazite","abrazitic","abrazo","abrazos","abreact","abreacted","abreacting","abreaction","abreactions","abreacts","abreast","abreed","abrege","abreid","abrenounce","abrenunciate","abrenunciation","abreption","abret","abreuvoir","abri","abrico","abricock","abricot","abridgable","abridge","abridgeable","abridged","abridgedly","abridgement","abridgements","abridger","abridgers","abridges","abridging","abridgment","abridgments","abrim","abrin","abrine","abris","abristle","abroach","abroad","abrocome","abrogable","abrogate","abrogated","abrogates","abrogating","abrogation","abrogations","abrogative","abrogator","abrogators","abronia","abrood","abrook","abrosia","abrosias","abrotanum","abrotin","abrotine","abrupt","abruptedly","abrupter","abruptest","abruptio","abruption","abruptiones","abruptions","abruptly","abruptness","abruptnesses","abs","absampere","absarokite","abscam","abscess","abscessed","abscesses","abscessing","abscession","abscessroot","abscind","abscise","abscised","abscises","abscisin","abscising","abscisins","abscision","absciss","abscissa","abscissae","abscissas","abscisse","abscissin","abscission","abscissions","absconce","abscond","absconded","abscondedly","abscondence","absconder","absconders","absconding","absconds","absconsa","abscoulomb","abscound","absee","abseil","abseiled","abseiling","abseils","absence","absences","absent","absentation","absented","absentee","absenteeism","absenteeisms","absentees","absenteeship","absenter","absenters","absentia","absenting","absently","absentment","absentminded","absentmindedly","absentmindedness","absentmindednesses","absentness","absents","absey","absfarad","abshenry","absinth","absinthe","absinthes","absinthial","absinthian","absinthiate","absinthiated","absinthiating","absinthic","absinthiin","absinthin","absinthine","absinthism","absinthismic","absinthium","absinthol","absinthole","absinths","absis","absist","absistos","absit","absmho","absohm","absoil","absolent","absolute","absolutely","absoluteness","absolutenesses","absoluter","absolutes","absolutest","absolution","absolutions","absolutism","absolutisms","absolutist","absolutista","absolutistic","absolutists","absolutive","absolutization","absolutize","absolutized","absolutizes","absolutizing","absolutory","absolvable","absolvatory","absolve","absolved","absolvent","absolver","absolvers","absolves","absolving","absolvitor","absolvitory","absonant","absonous","absorb","absorbabilities","absorbability","absorbable","absorbance","absorbances","absorbancies","absorbancy","absorbant","absorbants","absorbed","absorbedly","absorbedness","absorbefacient","absorbencies","absorbency","absorbent","absorbents","absorber","absorbers","absorbing","absorbingly","absorbition","absorbs","absorbtion","absorpt","absorptance","absorptances","absorptiometer","absorptiometric","absorption","absorptional","absorptions","absorptive","absorptively","absorptiveness","absorptivities","absorptivity","absquatulate","absquatulated","absquatulates","absquatulating","absquatulation","abstain","abstained","abstainer"
				};
				
				EaglercraftRandom rand = new EaglercraftRandom();
				String altName = "";
				String password = "";
				for(int x = 0; x < 2; x++) {
					String add = usernameDefaultWords[rand.nextInt(usernameDefaultWords.length)];
					add = add.substring(0,1).toUpperCase()+add.substring(1, add.length());
					altName = altName + add;
				}
				if(altName.length() >= 16) {
					altName = altName.substring(0,16);
				}
				for(int x = 0; x < 2; x++) {
					String add = usernameDefaultWords[rand.nextInt(usernameDefaultWords.length)];
					add = add.substring(0,1).toUpperCase()+add.substring(1, add.length());
					password = password + add + rand.nextInt(100-50)+50;
				}
				if(password.length() >= 16) {
					password = password.substring(0,16);
				}
				boolean contain = false;
				for(Alt a : alts) {
					if(a.username == altName) {
						contain = true;
						break;
					}
				}
				if(contain) {
					i--;
					continue;
				}
				alts.add(new Alt(altName, password));
			}
			} catch(Exception e) {
				NotifUtils.addNotification("Alt Manager", "Enter a number of alts to gen.", 1000, NotifType.ERROR);
			}
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if(par1GuiButton.id == 1) {
			for(Alt a : alts) {
				if(a.username == this.username.getText()) {
					NotifUtils.addNotification("Alt Manager", "This alt already exists.", 1000, NotifType.ERROR);
					return;
				}
			}
			if(this.username.getText().length() < 3) {
				NotifUtils.addNotification("Alt Manager", "Username too short.", 1000, NotifType.ERROR);
				return;
			}
			alts.add(new Alt(this.username.getText(), this.password.getText()));
			this.username.setFocused(true);
			this.username.setText("");
			NotifUtils.addNotification("Account added", "Successfully added account " + this.username.getText().substring(0, MathHelper.floor_float((float)this.username.getText().length()/2.1f))+this.username.getText().substring(MathHelper.floor_float((float)this.username.getText().length()/2.1f)).replaceAll(".", "\u25CF"), 1000, NotifType.INFO);
		}
		if(par1GuiButton.id == 2) {
			if(mc.theWorld != null) {
				mc.displayGuiScreen(new ClickGUI());
			} else {
				mc.displayGuiScreen(new GuiMainMenu());
			}
		}
		if(par1GuiButton.id == 4) {
			EaglercraftRandom rand = new EaglercraftRandom();
			int acc = rand.nextInt(alts.size());
			EaglerProfile.username = alts.get(acc).username;
		}
		if(par1GuiButton.id == 3) {
			genAlt(Integer.valueOf(this.genAlts.getText()));
		}
		super.actionPerformed(par1GuiButton);
	}

}
