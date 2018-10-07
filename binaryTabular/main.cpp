#include <iostream>
#include <bits/stdc++.h>
#include <algorithm>
#include <vector>
#include <queue>

using namespace std;

//Global Variables:
vector <vector<vector<pair<int,vector<int> > > > > everything;
vector<vector<pair<int,vector<int> > > > column;
vector<pair<int,vector<int> > > group;
pair<int,vector<int> > implicant;
vector<int> dontCare;
vector<pair<int,vector<int> > > primeImplicants;
vector<vector<pair<int,vector<int> > > > petrick;
queue <pair<int,vector<int> > > essentials;
vector<int> minterms;
int digits;

//Prototypes:
void setEverything();
void displayEverything();
bool isFound(int n);
void grouping(int col, int g1, int g2);
void createColumn(int col);
bool isEqualPairs(pair<int,vector<int> > p1, pair<int,vector<int> > p2);
int pairOccursAt(pair<int,vector<int> > p, vector< pair<int,vector<int> > > v);
void displayPIs();
void removeDuplicates();
bool IsSub(vector<int> v2,vector<int> v1);
void createPetrick ();
void multiply ( pair <int,vector<int> > implicant1, pair<int,vector<int> > implicant2, pair<int,vector<int> > result);
void removeEmpty();
string toLiterals(pair<int,vector<int> > imp);
void displayLiteralPIs();
void printPair(pair <int,vector<int> > implicant);

int main()
{
     cout << "Enter number of digits";
     cin >> digits;
     cout << "Enter minterms, enter -1 to end" << endl;
     int minterm;
     cin >> minterm;
     while(minterm >= pow(2,digits) ){
        cout << "Wrong minterm, Enter again";
        cin >> minterm;
     }
     minterms.push_back(minterm);

     while (minterm != -1){
        minterms.push_back(minterm);
        cin >> minterm;
        while(minterm >= pow(2,digits) ){
            cout << "Wrong minterm, Enter again";
            cin >> minterm;
        }
     }

     sort(minterms.begin(), minterms.end() );
     setEverything();
     for(int i=0; i< minterms.size() ;i++){
        std::bitset<sizeof(size_t)* CHAR_BIT> b(minterms[i]);
        int ones = b.count();
        implicant.first = 0;
        implicant.second.push_back(minterms[i]);
        everything[0][ones].push_back(implicant);
        primeImplicants.push_back(implicant);
        implicant.second.clear();
    }
    createColumn(0);
    removeDuplicates();
    displayEverything();
    displayPIs();
    displayLiteralPIs();
    petrick.reserve(minterms.size());

    return 0;
}

void setEverything(){
    for(int k=0;k<=10;k++){
        column.push_back(group);
        group.clear();
    }
    everything.push_back(column);
    column.clear();
}
void displayEverything(){
    for(int l = 0; l< everything.size() ; l++){
        cout<<endl << "Column" << l << endl;
        removeEmpty();
        for(int k=0; k<everything[l].size(); k++){
            removeEmpty();
            cout<<endl<<"Group"<<k<<endl;
                for(int j = 0 ; j< everything[l][k].size() ; j++){
                    int a = everything[l][k][j].first;
                    bitset<6> x(a);
                    cout << x;
                    cout<<"pair"<<x<<"(";
                    int i = 0;
                    for(i=0;i<everything[l][k][j].second.size() ;i++){
                        int b=everything[l][k][j].second[i] ;
                        bitset<6> x(b);
                        cout <<x << ", ";
                    }
                    cout << ")" << endl;
                }
        }
    }
}

void displayPIs(){
    removeDuplicates();
    cout << "Prime Implicants are : " << endl;
    for(int i = 0 ; i< primeImplicants.size() ; i++){
        printPair(primeImplicants[i]);
    }
}

void displayLiteralPIs(){
    cout << "Prime Implicants are:" << endl;
    for(int j = 0 ; j< primeImplicants.size() ; j++){
       string pi = toLiterals(primeImplicants[j]);
       cout << pi << endl;
    }
}

void grouping(int col, int g1, int g2){
	if(g2 == everything[col].size()){
        return;
    }
	 vector<pair<int,vector<int> > > res;
   	 vector < vector<pair<int,vector<int> > > > nextColumn;
   	 if(everything.size() > col){
        everything.push_back(nextColumn);
   	 }
    int nextCol = col+1;
	for(int i = 0; i<everything[col][g1].size();i++){
		for(int j = 0; j<everything[col][g2].size();j++){
			 if(everything[col][g1][i].first ==  everything[col][g2][j].first){
				int r1 = everything[col][g1][i].second[0] ^  everything[col][g2][j].second[0];
				if(isFound(r1)){
					if(col == 0){
                        implicant.first = r1;
					}else{
                        int r2 = r1|everything[col][g1][i].first;
                        implicant.first = r2;
					}
					implicant.second.insert(implicant.second.begin(),everything[col][g1][i].second.begin(), everything[col][g1][i].second.end());
					implicant.second.insert(implicant.second.end(),  everything[col][g2][j].second.begin(), everything[col][g2][j].second.end());
					sort(implicant.second.begin(), implicant.second.end());
                    res.push_back(implicant);
                    if(pairOccursAt(implicant, primeImplicants) == -1){
                        primeImplicants.push_back(implicant);
                    }
				}
                implicant.second.clear();
			}

        }
    }
    everything[nextCol].push_back(res);
    grouping(col, g2, g2+1);
}

void createColumn(int col){
    if(everything[col].size() == 0){
    return;
    }
    grouping(col,0,1);
    createColumn(col+1);
}
bool isFound(int n){
    std::bitset<sizeof(size_t)* CHAR_BIT> b(n);
    int ones = b.count();
    if(ones==1){
        return true;
    }
    return false;
}

bool isEqualPairs(pair<int,vector<int> > p1, pair<int,vector<int> > p2){
    if(p1.first == p2.first && p1.second == p2.second){
        return true;
    }
    return false;
}

int pairOccursAt(pair<int,vector<int> > p, vector< pair<int,vector<int> > > v){
    for(int i = 0; i<v.size(); i++){
        if(isEqualPairs(v[i], p)){
            return i;
        }
    }
    return -1;
}

void removeDuplicates(){
    for (int i = 0 ; i<primeImplicants.size(); i++){
        for (int j = i+1 ; j<primeImplicants.size(); j++){

            if(includes(primeImplicants[j].second.begin(), primeImplicants[j].second.end(),primeImplicants[i].second.begin(), primeImplicants[i].second.end())){
                 primeImplicants.erase(primeImplicants.begin()+i);
                 i=0;
                 j=i+1;
            }
        }
    }
}
bool IsSub(vector<int> v2,vector<int> v1){
    int counter=0;
    for(int i=0;i<v1.size();i++){
        for(int j=0;j<v2.size();j++){
            if(v1[i]==v2[j]){
                i++;
                counter++;
            }
            if (counter==0 && j == v2.size()-1) {
            return  false;
            }
        }
    }
    return true;
}

void createPetrick (){

    for (int i = 0; i < minterms.size() ; i++){
        for (int j = 0 ; j< primeImplicants.size(); j++){
            if(find(primeImplicants[j].second.begin(), primeImplicants[j].second.end(), minterms[i]) != primeImplicants[j].second.end() && primeImplicants[j].second.size() > 1 ){
                petrick[i].push_back(primeImplicants[j]);
            }
        }
    }
}


void multiply ( pair <int,vector<int> > implicant1, pair<int,vector<int> > implicant2, pair<int,vector<int> > result) {
    result.second.clear();
    result.first = implicant1.first | implicant2.first;
    for (int i = 0 ; i < implicant1.second.size(); i++) {
        if(find(implicant2.second.begin(), implicant2.second.end(), implicant1.second[i]) != implicant2.second.end()){
            result.second.push_back(implicant1.second[i]);
        }
    }
}

void findEssentials(int ind1, int ind2){

}

void removeEmpty(){
    for(int l = 0; l< everything.size() ; l++){
        for(int k=0; k<everything[l].size(); k++){
            if (everything[l][k].size() == 0){
                everything[l].erase(everything[l].begin() + k);
            }
        }
    }
    for(int l = 0; l< everything.size() ; l++){
        if (everything[l].size() == 0){
            everything.erase(everything.begin() + l);
        }
    }
}

string toLiterals(pair<int,vector<int> > imp){
    string res;
    int comparedWith = pow(2, digits-1);
    int bit;
    int bit2;
    char c = 'A';
    for(int i = 0; i<digits ; i++){
        bit = imp.first & comparedWith;
        if(bit == 0){
            bit2 = imp.second[0] & comparedWith;
            if(bit2 == 0){
                res+= c+i;
                res += '\'';
            }else{
                res+= c+i;
            }
        }
       comparedWith = comparedWith/2 ;
    }
    return res;
}

void printPair(pair <int,vector<int> > implicant){
    bitset<5> x(implicant.first);
    cout<< x << "(" ;
    for(int i = 0 ; i < implicant.second.size(); i++){
        cout << implicant.second[i] << ",";
    }
    cout <<")" << endl;
}

